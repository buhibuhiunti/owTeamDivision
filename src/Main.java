import TeamData.Block;
import TeamData.Member;
import TeamData.Team;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//タンクのみレート1500のメンバーでエラー出る
//ロール１個だけのやつが毎回同じチーム入る

public class Main {
    public static void main(String args[]) {
        //同じ階層にファイルがあれば読み込み
        //なければファイル配置後読み込み
        List<TeamData.Member> memberList = LoadDatasheet.loadDatasheet();

        System.out.print("1チームの人数を入力してください\n>");

        Scanner scanner = new Scanner(System.in);
        int maxPeople = scanner.nextInt();

        System.out.print("1チームのタンクの人数を入力してください\n>");
        int tankPeople = scanner.nextInt();

        System.out.print("1チームのダメージの人数を入力してください\n>");
        int damagePeople = scanner.nextInt();

        System.out.print("1チームのサポートの人数を入力してください\n>");
        int supportPeople = scanner.nextInt();
        System.out.print("同じチームに振り分けられるレート差の最大値を入力してください\n>");
        int upperLimitRateDifference = scanner.nextInt();
        System.out.print("マッチングの調整幅を入力してください\nデフォルトは500です\n>");
        int matchingAdjustmentRange = 500;
        if(scanner.hasNextInt()) {
            matchingAdjustmentRange = scanner.nextInt();
        }
        Block block = new Block(maxPeople,tankPeople,damagePeople,supportPeople,upperLimitRateDifference,matchingAdjustmentRange);
        for(int j = 1;j <= 3;j++) {
            for(TeamData.Member member : memberList) {
                int[] desiredRoleOrder = member.getDesiredRoleOrder();
                int numRoleSelected = 0;
                for(int i = 0;i < desiredRoleOrder.length;i++) {
                    if(desiredRoleOrder[i] != -1) {
                        numRoleSelected++;
                    }
                }
                if(numRoleSelected == j) {
                    block.inQue(member);
                }
            }
        }


        block.printTeamList();
    }


}
