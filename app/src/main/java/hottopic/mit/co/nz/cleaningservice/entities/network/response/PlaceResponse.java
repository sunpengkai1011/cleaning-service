package hottopic.mit.co.nz.cleaningservice.entities.network.response;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.map.PlaceInfo;

public class PlaceResponse implements Serializable{
    private List<PlaceInfo> candidates;

    public List<PlaceInfo> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<PlaceInfo> candidates) {
        this.candidates = candidates;
    }
}