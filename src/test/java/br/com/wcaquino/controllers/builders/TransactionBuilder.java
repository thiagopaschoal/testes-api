package br.com.wcaquino.controllers.builders;

import br.com.wcaquino.controllers.enums.TransactionType;
import br.com.wcaquino.controllers.models.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionBuilder {

    private Transaction elemento;
    private TransactionBuilder(){}

    public static TransactionBuilder oneTransaction(TransactionType type,
                                                    Long conta_id,
                                                    Long usuario_id,
                                                    Double valor,
                                                    String envolvido,
                                                    String descricao) {
        TransactionBuilder builder = new TransactionBuilder();
        builder.elemento = new Transaction(type, conta_id, usuario_id, valor, envolvido, descricao);
        return builder;
    }

    public TransactionBuilder withObservation(String param) {
        elemento.setObservacao(param);
        return this;
    }

    public TransactionBuilder withMovementDay(LocalDate param) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String newDate = param.format(formatter);
        elemento.setData_transacao(newDate);
        return this;
    }

    public TransactionBuilder withPayDay(LocalDate param) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String newDate = param.format(formatter);
        elemento.setData_pagamento(newDate);
        return this;
    }

    public TransactionBuilder withState(Boolean param) {
        elemento.setStatus(param);
        return this;
    }

    public TransactionBuilder withTransferId(Long param) {
        elemento.setTransferencia_id(param);
        return this;
    }

    public TransactionBuilder withInstallmentId(Long param) {
        elemento.setParcelamento_id(param);
        return this;
    }

    public Transaction build() {
        return elemento;
    }
}
