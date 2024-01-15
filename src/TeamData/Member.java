package TeamData;


import static TeamData.InQueRole.NODATA;

public class Member {
    private String battleTag = "NODATA";
    private int[] desiredRoleOrder = {-1,-1,-1};//TANK、DAMAGE、サポートの順
    private int[] rate = {-1,-1,-1};
    protected String duoBattleTag = "NODATA";
    protected String ngBattleTag = "NODATA";
    private boolean assigned = false;
    private InQueRole inQueRole = NODATA;
    private boolean leaderFlag = false;

    public Member(String battleTag, int[] desiredRoleOrder, int[] rate) {
        this.battleTag = battleTag;
        this.desiredRoleOrder = desiredRoleOrder;
        this.rate = rate;
    }
    private Member(Member member) {
        this(member.battleTag, member.getDesiredRoleOrder(), member.getRate());
        this.duoBattleTag = getDuoBattleTag();
        this.ngBattleTag = getNgBattleTag();
        this.assigned = getAssigned();
        this.inQueRole = getInQueRole();
        this.leaderFlag = getLeaderFlag();
    }
    public Member(String battleTag,int[] desiredRoleOrder,int[] rate,String duoBattleTag) {
        this(battleTag,desiredRoleOrder,rate);
        this.duoBattleTag = duoBattleTag;
    }
    public Member(String battleTag,String ngBattleTag, int[] desiredRoleOrder, int[] rate) {
        this(battleTag,desiredRoleOrder,rate);
        this.ngBattleTag = ngBattleTag;
    }
    public Member(String battleTag,int[] desiredRoleOrder,int[] rate,String duoBattleTag,String ngBattleTag){
        this(battleTag,desiredRoleOrder,rate,duoBattleTag);
            this.ngBattleTag = ngBattleTag;
    }
    public Member(boolean leaderFlag,String battleTag,int[] desiredRoleOrder,int[] rate) {
        this(battleTag,desiredRoleOrder,rate);
        if(leaderFlag) {
            this.leaderFlag = true;
        }
    }

    boolean getAssigned() {
        return this.assigned;
    }
    String getNgBattleTag() {
        return this.ngBattleTag;
    }
    String getDuoBattleTag() {
        return this.duoBattleTag;
    }
    void inQue(InQueRole inQueRole){
        assigned = true;
        this.inQueRole = inQueRole;
    }
    public int[] getDesiredRoleOrder(){
        return this.desiredRoleOrder.clone();
    }
    int[] getRate(){
        return this.rate.clone();
    }
    String getBattleTag(){
        return this.battleTag;
    }
    InQueRole getInQueRole() {
        return this.inQueRole;
    }
    boolean getLeaderFlag() {
        return this.leaderFlag;
    }
    Member deepCopy(Member member) {
        return new Member(member);
    }
}
