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
    Team(List<Member> teamMember, Team team) {
        this.teamMember = teamMember;
        this.averageRate = team.getAverageRate();
        this.maxRate = team.getMaxRate();
        this.minRate = team.getMinRate();
        this.currentMember = team.getCurrentMember();
        this.currentTankMember = team.getCurrentTankMember();
        this.currentDamageMember = team.getCurrentDamageMember();
        this.currentSupportMember = team.getCurrentSupportMember();
        this.MAX_PEOPLE = team.getMaxPeople();
        this.TANK_MAX_PEOPLE = team.getTankMaxPeople();
        this.DAMAGE_MAX_PEOPLE = team.getDamageMaxPeople();
        this.SUPPORT_MAX_PEOPLE = team.getSupportMaxPeople();
    }

    int getSupportMaxPeople() {
        return this.SUPPORT_MAX_PEOPLE;
    }
    int getDamageMaxPeople() {
        return this.DAMAGE_MAX_PEOPLE;
    }
    int getTankMaxPeople() {
        return this.TANK_MAX_PEOPLE;
    }
    int getMaxPeople() {
        return this.MAX_PEOPLE;
    }
    int getCurrentSupportMember() {
        return this.currentSupportMember;
    }
    int getCurrentDamageMember() {
        return this.currentDamageMember;
    }
    int getCurrentTankMember() {
        return this.currentTankMember;
    }
    int getCurrentMember() {
        return this.currentMember;
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
        if(averageRate != -1) {
            averageRate = (averageRate * currentMember + member.getRate()[role]) / (currentMember + 1);
        } else {
            averageRate = member.getRate()[role];
        }
        if(maxRate < member.getRate()[role]) {
            maxRate = member.getRate()[role];
        }
        if(minRate == -1 || minRate > member.getRate()[role]) {
            minRate = member.getRate()[role];
        }
        currentMember++;
        if(role == 0) {
            currentTankMember++;
        }
        if(role == 1) {
            currentDamageMember++;
        }
        if(role == 2) {
            currentSupportMember++;
        }
    }
    public void outputMemberList() {
        for(Member member : this.teamMember) {
            System.out.print("バトルタグ：" + member.getBattleTag() + "\tロール：" + member.getInQueRole() +
                    "\tレート：" + member.getRate()[0] + "　" + member.getRate()[1] + "　" + member.getRate()[2]);
            if(member.getLeaderFlag()) {
                System.out.print("カスタムリーダー");
            }
            System.out.print("\n");
        }
    }
    public int getAverageRate() {
        return averageRate;
    }

    public boolean roleIsFull(InQueRole inQueRole) {
        if(inQueRole == InQueRole.TANK && this.currentTankMember >= TANK_MAX_PEOPLE) {
            return true;
        }
        if(inQueRole == InQueRole.DAMAGE && this.currentDamageMember >= DAMAGE_MAX_PEOPLE) {
            return true;
        }
        if(inQueRole == InQueRole.SUPPORT && this.currentSupportMember >= SUPPORT_MAX_PEOPLE) {
            return true;
        }
        return isFull();
    }
    boolean isFull() {
        if(this.currentMember >= this.MAX_PEOPLE) {
            return true;
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
