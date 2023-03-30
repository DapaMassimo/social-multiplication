package microservices.book.multiplication.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GetProvaPayloadRequest {

    @NotNull
    String neededString;

    public String getNeededString() {
        return neededString;
    }

    public void setNeededString(String neededString) {
        this.neededString = neededString;
    }
}
