package com.serializacao;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gerenciadores.Gerenciador;
import com.usuarios.Usuario;

public class FuncoesSerial {
    private static final Path ARQUIVO_USUARIOS = Paths.get(System.getProperty("user.home"), ".taskit", "usuarios.ser");

    // Salva a lista inteira de usuários + mapa de senhas (snapshot).
    public static void salvarUsuarios(Gerenciador ger) {
        // prepara dados
        List<Usuario> usuarios = new ArrayList<>(ger.getUsuarios());
        Map<UUID, String> senhas = new HashMap<>();

        // Tenta obter as senhas de forma explícita (método público) se existir.
        try {
            Method exportMethod = ger.getClass().getMethod("exportarSenhas");
            Object exported = exportMethod.invoke(ger);
            if (exported instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<UUID, String> m = (Map<UUID, String>) exported;
                senhas.putAll(m);
            }
        } catch (ReflectiveOperationException ignored) {
            // fallback: tenta acessar o campo privado 'senhas' por reflection (compatibilidade)
            try {
                Field f = ger.getClass().getDeclaredField("senhas");
                f.setAccessible(true);
                Object raw = f.get(ger);
                if (raw instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<Object, Object> rawMap = (Map<Object, Object>) raw;
                    for (Map.Entry<Object, Object> e : rawMap.entrySet()) {
                        if (e.getKey() instanceof UUID && e.getValue() instanceof String) {
                            senhas.put((UUID) e.getKey(), (String) e.getValue());
                        }
                    }
                }
            } catch (ReflectiveOperationException ex) {
                // se falhar, gravaremos o snapshot com mapa de senhas vazio
            }
        }

        UsuariosSnapshot snap = new UsuariosSnapshot(usuarios, senhas);

        try {
            Path parent = ARQUIVO_USUARIOS.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (OutputStream os = Files.newOutputStream(ARQUIVO_USUARIOS, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING); ObjectOutputStream oos = new ObjectOutputStream(os)) {

                oos.writeObject(snap);
                System.out.println("Snapshot de usuários salvo com sucesso em: " + ARQUIVO_USUARIOS.toString());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar snapshot de usuários: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void carregarUsuarios(Gerenciador ger) {
        if (!Files.exists(ARQUIVO_USUARIOS)) {
            return;
        }

        try (InputStream is = Files.newInputStream(ARQUIVO_USUARIOS, StandardOpenOption.READ); ObjectInputStream ois = new ObjectInputStream(is)) {
            Object obj = ois.readObject();

            if (obj instanceof UsuariosSnapshot) {
                UsuariosSnapshot snap = (UsuariosSnapshot) obj;

                // injeta a lista de usuários
                ger.setUsuarios(new ArrayList<>(snap.getUsuarios()));

                // tenta injetar o mapa de senhas: primeiro método público, senão reflection no campo.
                Map<UUID, String> senhas = new HashMap<>(snap.getSenhas());
                boolean injected = false;

                try {
                    Method setSenhasMethod = ger.getClass().getMethod("setSenhas", Map.class);
                    setSenhasMethod.invoke(ger, senhas);
                    injected = true;
                } catch (ReflectiveOperationException ignored) {
                    // fallback para acessar campo privado
                    try {
                        Field f = ger.getClass().getDeclaredField("senhas");
                        f.setAccessible(true);
                        Object raw = f.get(ger);
                        if (raw instanceof Map) {
                            Map<UUID, String> mapaExistente = (Map<UUID, String>) raw;
                            mapaExistente.clear();
                            mapaExistente.putAll(senhas);
                            injected = true;
                        }
                    } catch (ReflectiveOperationException ex) {
                        // não foi possível injetar as senhas; mapa permanecerá vazio
                    }
                }

                if (!injected && !senhas.isEmpty()) {
                    System.err.println("Aviso: não foi possível injetar o mapa de senhas no Gerenciador; senhas não foram restauradas.");
                }

            } else if (obj instanceof List) {
                // formato antigo: lista simples de Usuario
                List<Usuario> usuariosCarregados = (List<Usuario>) obj;
                ger.setUsuarios(usuariosCarregados);
                // mapa de senhas fica vazio — comportamento de migração: exigir reset de senha no primeiro login é recomendado
                System.out.println("Arquivo de usuários antigo carregado (somente lista). Mapa de senhas vazio.");
            } else {
                System.err.println("Formato de arquivo de usuários desconhecido: " + (obj != null ? obj.getClass().getName() : "null"));
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar snapshot de usuários: " + e.getMessage());
        }
    }
}
