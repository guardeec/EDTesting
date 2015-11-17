package POJO;

/**
 * Created by root on 11/17/15.
 */
public class Result {
    private String attackId;
    private String attackTitle;

    public Result(String attackId, String attackTitle) {
        this.attackId = attackId;
        this.attackTitle = attackTitle;
    }

    public String getAttackId() {
        return attackId;
    }

    public void setAttackId(String attackId) {
        this.attackId = attackId;
    }

    public String getAttackTitle() {
        return attackTitle;
    }

    public void setAttackTitle(String attackTitle) {
        this.attackTitle = attackTitle;
    }
}
