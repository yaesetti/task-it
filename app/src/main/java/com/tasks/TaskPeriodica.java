package com.tasks;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

import com.excecoes.DataInvalidaException;

public class TaskPeriodica extends Task {
    private Duration recorrencia;
    private LocalDateTime dataFinal;

    public TaskPeriodica(String titulo, String descricao, Categoria categoria, LocalDateTime data, ArrayList<Usuario> usuariosDonos, Duration recorrencia, LocalDateTime dataFinal) throws DataInvalidaException {
        super(titulo, descricao, categoria, data, usuariosDonos);
        this.recorrencia = recorrencia;
        if (dataFinal.isBefore(this.getData())) {
            throw new DataInvalidaException("Data Invalida: A data final de uma Task nao pode ser no passado!");
            this.dataFinal = null;
        }
        else {
            this.dataFinal = dataFinal;
        }
    }

    public void resetTask() {
        this.setFeito(false);
        this.setFeitoTodasSubtarefas(false);
        LocalDateTime novaData = this.getData().plus(this.recorrencia);
        this.setData(novaData);
    }
}
