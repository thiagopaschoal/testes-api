package br.com.wcaquino.controllers.models;

import br.com.wcaquino.controllers.enums.TransactionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private TransactionType tipo;
    private String descricao;
    private String envolvido;
    private String observacao;
    private String data_transacao;
    private String data_pagamento;
    private Double valor;
    private Boolean status;
    private Long conta_id;
    private Long usuario_id;
    private Long transferencia_id;
    private Long parcelamento_id;

    public Transaction(TransactionType type, Long conta_id, Long usuario_id, Double valor, String envolvido, String descricao) {
        this.tipo = type;
        this.conta_id = conta_id;
        this.usuario_id = usuario_id;
        this.valor = valor;
        this.envolvido = envolvido;
        this.descricao = descricao;
        this.data_transacao = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public TransactionType getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEnvolvido() {
        return envolvido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getData_transacao() {
        return data_transacao;
    }

    public void setData_transacao(String data_transacao) {
        this.data_transacao = data_transacao;
    }

    public String getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(String data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public Double getValor() {
        return valor;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getConta_id() {
        return conta_id;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public Long getTransferencia_id() {
        return transferencia_id;
    }

    public void setTransferencia_id(Long transferencia_id) {
        this.transferencia_id = transferencia_id;
    }

    public Long getParcelamento_id() {
        return parcelamento_id;
    }

    public void setParcelamento_id(Long parcelamento_id) {
        this.parcelamento_id = parcelamento_id;
    }
}

