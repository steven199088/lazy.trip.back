package friend.json;

import java.util.List;
import java.util.Map;

public class MemberStatusBatch {

    private String status; // online-batch
    private Map<Integer, List<Integer>> onlineMembers; // 上線者：共同聊天室 list

    public MemberStatusBatch(String status, Map<Integer, List<Integer>> onlineMembers) {
        this.status = status;
        this.onlineMembers = onlineMembers;
    }
}
