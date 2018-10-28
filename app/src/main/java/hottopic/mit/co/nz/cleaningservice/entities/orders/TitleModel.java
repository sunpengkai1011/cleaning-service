package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;

public class TitleModel implements Serializable{
    private String title;

    public TitleModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
