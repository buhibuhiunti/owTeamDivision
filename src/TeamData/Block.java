package TeamData;

import java.util.ArrayList;
import java.util.List;

public class Block {
    List<LeftTeam> leftTeams = new ArrayList<>();
    List<LightTeam> lightTeams = new ArrayList<>();
    final int MAX_PEOPLE;
    final int TANK_PEOPLE;
    final int DAMAGE_PEOPLE;
    final int SUPPORT_PEOPLE;
    final int UPPER_LIMIT_RATE_DIFFERENCE;
    final int MATCHING_ADJUSTMENT_RANGE;
    public Block(int maxPeople, int tankPeople, int damagePeople, int supportPeople,int upperLimitRateDifference,int matchingAdjustmentRange) {
        this.MAX_PEOPLE = maxPeople;
        this.TANK_PEOPLE = tankPeople;
        this.DAMAGE_PEOPLE = damagePeople;
        this.SUPPORT_PEOPLE = supportPeople;
        this.leftTeams.add(new LeftTeam());
        this.lightTeams.add(new LightTeam());
        this.UPPER_LIMIT_RATE_DIFFERENCE = upperLimitRateDifference;
        this.MATCHING_ADJUSTMENT_RANGE = matchingAdjustmentRange;
    }
    void newMatch(Member member,InQueRole inQueRole){
        this.leftTeams.add(new LeftTeam(member,inQueRole));
        this.lightTeams.add(new LightTeam());
    }
    public static void inQue(Member member) {
        int[] rate = member.getRate();
        if(rate[1] == -1) {
            
        }
    }

}
