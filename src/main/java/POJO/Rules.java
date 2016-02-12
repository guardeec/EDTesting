package POJO;

import java.util.List;

/**
 * Created by root on 11/16/15.
 */
public class Rules {
    private String id;
    private String functionalId;
    private List<String> hackerId;
    private String attackId;

    public String getAttackName() {
        return attackName;
    }

    public void setAttackName(String attackName) {
        this.attackName = attackName;
    }

    private String attackName;

    public Rules(String id, String functionalId, List<String> hackerId, String attackId) {
        this.id = id;
        this.functionalId = functionalId;
        this.hackerId = hackerId;
        this.attackId = attackId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionalId() {
        return functionalId;
    }

    public void setFunctionalId(String functionalId) {
        this.functionalId = functionalId;
    }

    public List<String> getHackerId() {
        return hackerId;
    }

    public void setHackerId(List<String> hackerId) {
        this.hackerId = hackerId;
    }

    public String getAttackId() {
        return attackId;
    }

    public void setAttackId(String attackId) {
        this.attackId = attackId;
    }
}
