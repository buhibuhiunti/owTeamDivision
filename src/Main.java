import TeamData.Block;
import TeamData.Member;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        //同じ階層にファイルがあれば読み込み
        //なければファイル配置後読み込み
        List<TeamData.Member> memberList = LoadDatasheet.loadDatasheet();

        System.out.println("1チームの人数を入力してください");

        Scanner scanner = new Scanner(System.in);
        int maxPeople = scanner.nextInt();

        System.out.println("1チームのタンクの人数を入力してください");
        int tankPeople = scanner.nextInt();

        System.out.println("1チームのダメージの人数を入力してください");
        int damagePeople = scanner.nextInt();

        System.out.println("1チームのサポートの人数を入力してください");
        int supportPeople = scanner.nextInt();
        System.out.println("同じチームに振り分けられるレート差の最大値を入力してください");
        int upperLimitRateDifference = scanner.nextInt();
        System.out.println("マッチングの調整幅を入力してください\nよく分からなければ500を入力してください");
        int matchingAdjustmentRange = scanner.nextInt();
        Block block = new Block(maxPeople,tankPeople,damagePeople,supportPeople,upperLimitRateDifference,matchingAdjustmentRange);
        for(TeamData.Member member : memberList) {
            int[] desiredRoleOrder = member.getDesiredRoleOrder();
            if(desiredRoleOrder[1] == -1) {
                TeamData.Block.inQue(member);
            }
        }
    }


}
