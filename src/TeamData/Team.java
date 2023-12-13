package TeamData;

import java.util.ArrayList;
import java.util.List;

public abstract class Team {
    List<Member> teamMember = new ArrayList<Member>();
    int averageRate = -1;
    int maxRate = -1;
    int minRate = -1;
    int currentMember = 0;
    int currentTankMember = 0;
    int currentDamageMember = 0;
    int currentSupportMember = 0;
    final int MAX_PEOPLE;
    final int TANK_MAX_PEOPLE;
    final int DAMAGE_MAX_PEOPLE;
    final int SUPPORT_MAX_PEOPLE;

    public Team(int maxPeople, int tankMaxPeople, int damageMaxPeople, int supportMaxPeople) {
        this.MAX_PEOPLE = maxPeople;
        this.TANK_MAX_PEOPLE = tankMaxPeople;
        this.DAMAGE_MAX_PEOPLE = damageMaxPeople;
        this.SUPPORT_MAX_PEOPLE = supportMaxPeople;
    }

    public void newEntry(Member member,InQueRole inQueRole) {
        teamMember.add(member);
        member.inQue(inQueRole);
        switch (inQueRole) {
            case TANK:
                addMember(0,member);
                break;
            case DAMAGE:
                addMember(1,member);
                break;
            case SUPPORT:
                addMember(2,member);
                break;
        }
    }
    private void addMember(int role,Member member) {
        this.currentSupportMember++;
        if(averageRate != -1) {
            averageRate = (averageRate * currentMember + member.getRate()[role]) / currentMember++;
        } else {
            averageRate = member.getRate()[role];
        }
        if(maxRate < member.getRate()[role]) {
            maxRate = member.getRate()[role];
        }
        if(minRate > member.getRate()[role]) {
            minRate = member.getRate()[role];
        }
        currentMember++;
    }
    public void outputMemberList() {
        for(Member member : this.teamMember) {
            System.out.println(member.getBattleTag());
        }
    }
    public int getAverageRate() {
        return averageRate;
    }

    public boolean roleIsFull(InQueRole inQueRole) {
        if(this.currentMember < MAX_PEOPLE) {
            if(inQueRole == InQueRole.TANK && this.currentTankMember > TANK_MAX_PEOPLE) {
                return true;
            }
            if(inQueRole == InQueRole.DAMAGE && this.currentDamageMember > DAMAGE_MAX_PEOPLE) {
                return true;
            }
            if(inQueRole == InQueRole.SUPPORT && this.currentSupportMember > SUPPORT_MAX_PEOPLE) {
                return true;
            }
        }
        return false;
    }
    public int getMaxRate() {
        return this.maxRate;
    }
    public int getMinRate() {
        return this.minRate;
    }
}
