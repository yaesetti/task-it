package com.tasks;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.excecoes.DataInvalidaException;

public class TaskPeriodica extends TaskAbstrata {
    private Duration recorrencia;
    private LocalDateTime dataFinal;

    public TaskPeriodica(String titulo, String descricao, Categoria categoria, LocalDateTime data, List<Usuario> usuariosDonos, Duration recorrencia, LocalDateTime dataFinal) throws DataInvalidaException {
        super(titulo, descricao, categoria, data, usuariosDonos);

        if (recorrencia == null) {
            throw new DataInvalidaException("Data invalida: A recorrencia nao pode ser vazia!");
        }
        if (recorrencia.isNegative()) {
            throw new DataInvalidaException("Data Invalida: A recorrencia deve ser um periodo maior que zero!");
        }
        this.recorrencia = recorrencia;

        if (dataFinal == null) {
            throw new DataInvalidaException("Data Invalida: A data final de uma Task nao pode ser vazia!");
        }
        if (dataFinal.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("Data Invalida: A data final de uma Task nao pode ser no passado!");
        }
        this.dataFinal = dataFinal;
    }

    public TaskPeriodica(String titulo, String descricao, Categoria categoria, LocalDateTime data, List<Usuario> usuariosDonos, Duration recorrencia) throws DataInvalidaException {
        super(titulo, descricao, categoria, data, usuariosDonos);

        if (recorrencia == null) {
            throw new DataInvalidaException("Data invalida: A recorrencia nao pode ser vazia!");
        }
        if (recorrencia.isNegative()) {
            throw new DataInvalidaException("Data Invalida: A recorrencia deve ser um periodo maior que zero!");
        }
        this.recorrencia = recorrencia;

        this.dataFinal = null;
    }

    public void resetTask() {
        this.setFeito(false);
        this.setFeitoTodasSubtarefas(false);
        LocalDateTime novaData = this.getData().plus(this.recorrencia);
        this.setData(novaData);
    }

    public LocalDateTime getDataFinal() {
        return this.dataFinal;
    }

    public Duration getRecorrencia() {
        return this.recorrencia;
    }

    public void setDataFinal(LocalDateTime novaDataFinal) {
        this.dataFinal = novaDataFinal;
    }

    public void setRecorrencia(Duration novaRecorrencia) {
        this.recorrencia = novaRecorrencia;
    }
}
