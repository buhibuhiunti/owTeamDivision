package TeamData;


import static TeamData.InQueRole.NODATA;

public class Member {
    private String battleTag = "NODATA";
    private int[] desiredRoleOrder = {-1,-1,-1};//タンク、ダメージ、サポートの順
    private int[] rate = {-1,-1,-1};
    protected String duoBattleTag = "NODATA";
    protected String ngBattleTag = "NODATA";
    private boolean assigned = false;
    private InQueRole inQueRole = NODATA;

    public Member(String battleTag, int[] desiredRoleOrder, int[] rate) {
        this.battleTag = battleTag;
        this.desiredRoleOrder = desiredRoleOrder;
        this.rate = rate;
    }
    public Member(String battleTag,int[] desiredRoleOrder,int[] rate,String duoBattleTag) {
        this(battleTag,desiredRoleOrder,rate);
        this.duoBattleTag = duoBattleTag;
    }
    public Member(String battleTag, int[] desiredRoleOrder, int[] rate, String ngBattleTag, boolean a) {
        this(battleTag,desiredRoleOrder,rate);
        this.ngBattleTag = ngBattleTag;
    }
    public Member(String battleTag,int[] desiredRoleOrder,int[] rate,String duoBattleTag,String ngBattleTag){
        this(battleTag,desiredRoleOrder,rate,duoBattleTag);
            this.ngBattleTag = ngBattleTag;
    }
    public void inQue(InQueRole inQueRole){
        assigned = true;
        this.inQueRole = inQueRole;
    }
    public int[] getDesiredRoleOrder(){
        return this.desiredRoleOrder.clone();
    }
    public int[] getRate(){
        return this.rate.clone();
    }

}
