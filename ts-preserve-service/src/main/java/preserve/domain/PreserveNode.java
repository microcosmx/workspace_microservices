package preserve.domain;

import java.util.UUID;

/**
 * 存储接收到的请求，一个random的uuid和其对应请求的loginId
 */
public class PreserveNode {

    private UUID uuid;
    private String loginId;

    public PreserveNode(UUID uuid, String loginId){
        this.uuid = uuid;
        this.loginId = loginId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PreserveNode other = (PreserveNode) obj;
        return this.getUuid().equals(other.getUuid())
                && this.getLoginId().equals(other.getLoginId());
    }


}
