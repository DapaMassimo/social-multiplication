package microservices.book.multiplication.domain;

public class ProvaPayloadResponse {

    private boolean sonoBello;

    private Integer provaInteger;

    private Boolean sonoBelloWrapper;

    public Integer getProvaInteger() {
        return provaInteger;
    }

    public void setProvaInteger(Integer provaInteger) {
        this.provaInteger = provaInteger;
    }

    public Boolean getSonoBelloWrapper() {
        return sonoBelloWrapper;
    }

    public void setSonoBelloWrapper(Boolean sonoBelloWrapper) {
        this.sonoBelloWrapper = sonoBelloWrapper;
    }

    public boolean isSonoBello() {
        return sonoBello;
    }

    public void setSonoBello(boolean sonoBello) {
        this.sonoBello = sonoBello;
    }
}
