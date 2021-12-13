import java.util.InputMismatchException;
import java.util.Scanner;
public class GradeStudent {
    //Tao mot mang luu diem tong ket cua cac mon
    public static double[] weightedScore = new double[3];
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        begin();
        int midTerm = midTerm(sc);
        int finalTerm = finalTerm(sc,midTerm);
        homeWork(sc,midTerm, finalTerm);
        report();
    }

    //Hien thi thong diep chao mung
    public static void begin()
    {
        System.out.println("This program reads exam/homework scores and reports your overall course grade");
    }

    //Nhap va tinh toan diem giua ki
    public static int midTerm(Scanner sc)
    {
        System.out.println("\nMidterm:");
        return termScore(sc,0,0);
    }

    //Nhap va tinh toan diem cuoi ki
    public static int finalTerm(Scanner sc, int midTerm)
    {
        System.out.println("\nFinalterm:");
        return termScore(sc,1,  midTerm);
    }

    //Tinh toan diem
    public static int termScore(Scanner sc,int i, int midTerm)
    {
        try {
            int weight, shifted;
            int shiftAmount = 0;
            //Nhap nhung thong tin can thiet
            while (true) {
                System.out.print("Weight(0-100): ");
                weight = sc.nextInt();
                //Kiem tra xem max va weight da duoc dien dung chua -> neu chua se yeu cau nguoi dung nhap lai
                if (weight > 100 || weight < 0 || (midTerm + weight >= 100)) {
                    System.out.println("Invalid weight value!!");
                } else {
                    break;
                }
            }
            System.out.print("Score earned: ");
            int score = sc.nextInt();
            while (true) {
                System.out.print("Were the score shifted(1=yes 2=no)? ");
                shifted = sc.nextInt();
                if (shifted == 1 || shifted == 2)
                    break;
                else
                    System.out.println("Invalid shifted value!!");
            }
            if (shifted == 1) {
                System.out.print("Shift amount? ");
                shiftAmount = sc.nextInt();
            }
            //Tinh totalPoint = diem + diem cong
            int totalPoint = score + shiftAmount;
            //Neu diem tong > 100 -> dat lai diem = 100
            if (totalPoint > 100)
                totalPoint = 100;
            System.out.println("Total points = " + totalPoint + "/100");
            //Weighted score = tong diem / 100 * Weight + lam tron den so thap phan thu nhat
            System.out.printf("Weighted score = %.1f/%d\n", ((double) totalPoint / 100 * weight), weight);
            //luu weightedScore vao mang weightedScore
            weightedScore[i] = (double) totalPoint / 100 * weight;
            return weight;
        }
        //Sau khi bat duoc loi -> chay lai chuong trinh 1 lan nua
        catch(InputMismatchException e)
        {
            System.out.println("Wrong type!! Please start again!! \n");
            sc.next();
            return termScore(sc,i,midTerm);
        }
    }


    public static void homeWork(Scanner sc, int midTerm, int finalTerm )
    {
        try {
            System.out.println("\nHomework:");
            //Tong 3 weight = 100
            int weight = 100 - midTerm - finalTerm;
            System.out.println("Weight (0-100)? " + weight);
            //Nhap so assignment
            System.out.print("Number of assignments? ");
            int number = sc.nextInt();
            double score = 0;
            int max = 0;
            // Su dung vong lap de luu tong cac diem vao bien score va max
            double tempScore;
            int tempMax;
            for (int i = 0; i < number; i++) {
                //Kiem tra score va max da duoc dien dung chua-> neu chua se yeu cau nguoi dung nhap lai
                while (true) {
                    System.out.print("Assignment " + (i + 1) + " score and max? ");
                    tempScore = sc.nextInt();
                    tempMax = sc.nextInt();
                    if (tempScore <= tempMax && tempScore > 0)
                        break;
                    else
                        System.out.println("Invalid score and max");
                }
                score += tempScore;
                max += tempMax;
            }
            // Neu max > 150 -> dat lai max = 150 + chia lai score theo max
            if (max > 150) {
                score = score / max * 150;
                max = 150;
            }
            System.out.print("How many sections did you attend? ");
            int sectionPoint = sc.nextInt() * 5;
            //Neu sectionPoint > 30 -> dat lai section point
            if (sectionPoint > 30)
                sectionPoint = 30;
            System.out.println("Section points = " + sectionPoint + "/30");
            double totalPoint = score + sectionPoint;
            int totalMax = max + 30;
            System.out.println("Total points = " + Math.round(totalPoint) + "/" + totalMax);
            //Weighted score = tong diem/tong max * weight -> lam tron den so thap phan thu nhat
            System.out.printf("Weighted score = %.1f/%d\n", totalPoint / totalMax * weight, weight);
            weightedScore[2] = totalPoint / totalMax * weight;
        }
        ////Sau khi bat duoc loi -> chay lai chuong trinh 1 lan nua
        catch(InputMismatchException e)
        {
            System.out.println("Wrong type!! Please start again\n");
            sc.next();
            homeWork(sc,midTerm,finalTerm);
        }
    }

    //tinh toan va the hien ket qua GPA
    public static void report()
    {
        double sum = 0;
        //Luu tong diem cua cac phan vao bien sum
        for(int i = 0; i<3 ; i++)
        {
           sum+=weightedScore[i];
        }
        System.out.printf("\nOverall percentage: %.1f\n",sum);
        //So sanh sum de tinh diem GPA va neu nhan xet
        if(sum>=85) {
            System.out.println("Your grade will be at least: 3.0");
            System.out.println("Good");
        }
        else if(sum>=75) {
            System.out.println("Your grade will be at least: 2.0");
            System.out.println("Above average");
        }
        else if(sum>=60) {
            System.out.println("Your grade will be at least: 1.0");
            System.out.println("Normal");
        }
        else {
            System.out.println("Your grade will be at least: 0.0");
            System.out.println("Bad");
        }
    }
}
