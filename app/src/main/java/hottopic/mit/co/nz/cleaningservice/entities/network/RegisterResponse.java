package hottopic.mit.co.nz.cleaningservice.entities.network;

import java.io.Serializable;

/**
 * The registration response entity.
 */
public class RegisterResponse extends BaseResponse implements Serializable {
    private Boolean result;
    public Boolean getResult() {
        return result;
    }
}
