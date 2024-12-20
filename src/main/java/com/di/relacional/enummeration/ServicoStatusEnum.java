package com.di.relacional.enummeration;

public enum ServicoStatusEnum {
    ABERTO("A"),
    CONCLUIDO("C");
    private String status;

    ServicoStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
