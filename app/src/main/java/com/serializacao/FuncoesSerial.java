package com.serializacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gerenciadores.Gerenciador;
import com.usuarios.Usuario;

/**
 * Funções de serialização para salvar/carregar o Gerenciador completo.
 * Arquivo salvo em: {working-dir}/dados/gerenciador.ser
 * (Pode ser alterado para user.home/.taskit/gerenciador.ser se preferir)
 */
public final class FuncoesSerial {

    private static final Path ARQUIVO_GERENCIADOR = Paths.get(System.getProperty("user.dir"), "dados", "gerenciador.ser");

    private FuncoesSerial() { /* utilitário */ }

    /**
     * Salva o Gerenciador de forma atômica: escreve num ficheiro temporário e depois move.
     */
    public static void salvarGerenciador(Gerenciador ger) {
        if (ger == null) return;

        try {
            Path parent = ARQUIVO_GERENCIADOR.getParent();
            if (parent != null) Files.createDirectories(parent);

            Path tmp = Files.createTempFile(parent, "gerenciador", ".tmp");
            try (OutputStream os = Files.newOutputStream(tmp, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                 ObjectOutputStream oos = new ObjectOutputStream(os)) {
                oos.writeObject(ger);
                oos.flush();
            }

            try {
                Files.move(tmp, ARQUIVO_GERENCIADOR, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(tmp, ARQUIVO_GERENCIADOR, StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("Gerenciador salvo em: " + ARQUIVO_GERENCIADOR.toString());
        } catch (IOException e) {
            System.err.println("Erro ao salvar gerenciador: " + e.getMessage());
        }
    }

    /**
     * Carrega o Gerenciador do disco. Retorna a instância desserializada ou null se não existir/erro.
     * Também tenta detectar formatos antigos (UsuariosSnapshot / List<Usuario>) e convertê-los.
     */
    @SuppressWarnings("unchecked")
    public static Gerenciador carregarGerenciador() {
        if (!Files.exists(ARQUIVO_GERENCIADOR)) {
            return null;
        }

        try (InputStream is = Files.newInputStream(ARQUIVO_GERENCIADOR, StandardOpenOption.READ);
             ObjectInputStream ois = new ObjectInputStream(is)) {

            Object obj = ois.readObject();

            if (obj instanceof Gerenciador) {
                return (Gerenciador) obj;
            }

            // Fallbacks para formatos antigos:
            if (obj instanceof UsuariosSnapshot) {
                UsuariosSnapshot snap = (UsuariosSnapshot) obj;
                Gerenciador ger = new Gerenciador();
                ger.setUsuarios(new ArrayList<>(snap.getUsuarios()));
                ger.setSenhas(new HashMap<>(snap.getSenhas()));
                return ger;
            }

            if (obj instanceof List) {
                // arquivo antigo contendo só lista de Usuario
                List<Usuario> usuariosCarregados = (List<Usuario>) obj;
                Gerenciador ger = new Gerenciador();
                ger.setUsuarios(usuariosCarregados);
                // senhas não disponíveis -> mapa vazio
                return ger;
            }

            System.err.println("Formato de arquivo de gerenciador desconhecido: " + (obj != null ? obj.getClass().getName() : "null"));
            return null;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar gerenciador: " + e.getMessage());
            return null;
        }
    }
}
