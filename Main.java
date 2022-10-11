

import java.util.*;
import java.io.*;

public class Main {

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args){
        utama();
    }

    static void utama(){
        System.out.println("\n========== SOLUSI SPL dan Operasi MATRIKS ===========");
        System.out.println("1. Metode Eliminasi Gauss-Jordan");
        System.out.println("2. Metode Eliminasi Gauss");
        System.out.println("3. Metode chramer");
        System.out.println("4. Metode Invers");
        System.out.println("5. Perkalian antar Matriks");
        System.out.println("6. Perkalian Matriks dengan konstanta");
        System.out.println("7. Transpose Matriks");
        System.out.println("8. Penjumlahan Matriks");
        System.out.println("9. Pengurangan Matriks");
        System.out.println("10. Keluar\n");
        System.out.print("Masukkan pilihan: ");
        int pilihan = in.nextInt();
        while(true){
            if (pilihan == 1) gaussjordan();
            else if (pilihan == 2) gauss();
            else if (pilihan == 3) cramer();
            else if (pilihan == 4) balikan();
            else if (pilihan == 5) Perkalian();
            else if (pilihan == 6) PerkalianConst();
            else if (pilihan == 7) Transpose();
            else if (pilihan == 8) penjumlahanMatriks();
            else if (pilihan == 9) penguranganMatriks();
            else if(pilihan == 10) System.exit(0);
            else{
                System.out.println("Masukan salah. Silakan masukkan ulang!");
                pilihan = in.nextInt();
            }
        }
    }

    static void masukanelim(Matriks M){

                System.out.print("Masukan Jumlah Baris: ");
                int n = in.nextInt();
                System.out.print("Masukan Jumlah Kolom: ");
                int m = in.nextInt();
                M.SetBrs(n);
                M.SetKol(m+1);
                M.keyboardSPL(n,m+1);
    }

    static void masukanlain(Matriks M, double[] konstanta){
                System.out.print("Masukan Ordo Matriks: ");
                int n = in.nextInt();
                int m = n;
                M.SetBrs(n);
                M.SetKol(m);
                M.bacaKoefKeyboard(konstanta, n, m+1);
    }

    static void tulisMatriksAugmented(Matriks M){
        System.out.println("Matriks Augmented: ");
        M.tulisMatriks();
        System.out.println();
    }

    static void gauss(){
        System.out.println("\n=== Eliminasi Gauss ===");
        Matriks M = new Matriks();
        masukanelim(M);
        tulisMatriksAugmented(M);
        Matriks Mmanip = new Matriks(M.GetBrs(),M.GetKol());
        M.salinMatriks(Mmanip);

        M.eliminasiGauss();
        System.out.println("Matriks Eselon Baris: ");
        M.tulisMatriks();  
        double[][] solusi = new double[100][100];
        int[] jumlahSolusi = new int[1];

        Mmanip.eliminasiGaussJordan();
        System.out.println();                          
        //Menuliskan solusi
        Mmanip.solusiEliminasiGaussJordan(solusi, jumlahSolusi);
        Mmanip.tulisSolusi(solusi);
        System.out.println();
        System.out.println("Kembali ke dalam pilihan....");
        utama();

    }

    static void gaussjordan(){
        System.out.println("\n=== Eliminasi Gauss-Jordan ===");
        Matriks M = new Matriks();
        masukanelim(M);
        tulisMatriksAugmented(M);

        M.eliminasiGaussJordan();
        System.out.println("Matriks Eselon Baris Tereduksi: ");
        M.tulisMatriks();  
        double[][] solusi = new double[100][100];
        int[] jumlahSolusi = new int[1];

        System.out.println();                          
        //Menuliskan solusi
        M.solusiEliminasiGaussJordan(solusi, jumlahSolusi);
        M.tulisSolusi(solusi);
        System.out.println();
        System.out.println("Kembali ke dalam pilihan....");
        utama();
    }

    static void balikan(){
        System.out.println("\n=== Metode Matriks Invers ===");
        Matriks M = new Matriks();
        Matriks Mtemp = new Matriks();
        Matriks Mhasil = new Matriks();
        double[] konstanta = new double[100];
        masukanlain(M, konstanta);

        if(M.GetBrs() == M.GetKol()){
        //Menerapkan eliminasi Gauss-Jordan untuk memindahkan matriks identitas ke kiri
            Mtemp.SetBrs(M.GetBrs());
            Mtemp.SetKol(2*M.GetKol());
            M.salinMatriks(Mtemp);
            M.isiIdentitas(Mtemp);
            Mtemp.eliminasiGaussJordan();

            if (Mtemp.cekDiagonalInvers()){
                System.out.println("\nMatriks hasil operasi baris elementer: ");
                Mtemp.tulisMatriks();
                System.out.println();
            
                //Menuliskan hasil sistem persamaan
                Mhasil.SetBrs(Mtemp.GetBrs());
                Mhasil.SetKol(1);
                Mtemp.kaliMatriksKonstanta(konstanta, Mhasil);
                Mhasil.tulisHasilSPLIvnvers();
            }else {
                System.out.println("\nMetode Matriks Balikan tidak dapat menyelesaikan persamaan.");
            }
        }else {
            System.out.println("\nMetode Matriks Balikan tidak dapat menyelesaikan persamaan.");
        }

        System.out.println();
        System.out.println("Kembali ke dalam pilihan....");
        utama();
    }
    static void determinan(){
        System.out.println("\n=== Determinan matrix ===");
        Matriks M = new Matriks();
        double[] konstanta = new double[100];
        masukanlain(M, konstanta);
        double detM = M.determinanM();
        
        System.out.println(detM);
         
    }
    static void cramer(){
        System.out.println("\n=== Kaidah Cramer ===");
        Matriks M = new Matriks();
        Matriks Mtemp = new Matriks();
        double[] konstanta = new double[100];
        masukanlain(M, konstanta);

        Mtemp.SetBrs(M.GetBrs());
        Mtemp.SetKol(M.GetKol());
        double[] detMi = new double[M.GetKol()+1];

        //Menghitung deteminan M
        M.salinMatriks(Mtemp);
        double detM = M.determinanM();
        System.out.println();

        if (M.GetBrs() == M.GetKol()){
            if ((detM*10)%10 == 0)  System.out.printf("det(M) = %.0f\n", detM);
            else System.out.printf("det(M) = %.2f\n", detM);

            //Menghitung Determinan D(i)
            for(int j=1; j<=M.GetKol(); j++){
                Mtemp.salinMatriks(M);
                M.ubahKol(konstanta,j);
                detMi[j] = M.determinanM();
                if((detMi[j]*10)%10 == 0) System.out.printf("det(M%d) = %.0f\n", j, detMi[j]);
                else System.out.printf("det(M%d) = %.2f\n", j, detMi[j]);
            }

            //Menuliskan solusi tunggal jika ada
            if (detM != 0){
                System.out.println("\nSolusi Sistem Persamaan: ");
                for(int j=1; j<=M.GetKol(); j++){
                    if(((detMi[j]/detM)*10)%10 == 0) System.out.printf("x[%d] = %.0f\n", j, (detMi[j]/detM)); 
                    else System.out.printf("x[%d] = %.2f\n", j, (detMi[j]/detM));
                }
            }else {
                System.out.println("\nKaidah Cramer tidak dapat menyelesaikan persamaan");
            } 
        } else {
            System.out.println("Kaidah Cramer tidak dapat menyelesaikan persamaan");
        } 

        System.out.println();
        System.out.println("Kembali ke dalam pilihan....");
        utama();
    }
    static void Perkalian(){
        int i, j, k, rowM1, columnM1, rowM2, columnM2;
        double jumlah = 0;
        System.out.println("\n=== Perkalian 2 Matriks ===");
        Scanner scan = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris matriks pertama: ");
        rowM1 = scan.nextInt();
        System.out.print("Masukkan jumlah kolom matriks pertama: ");
        columnM1 = scan.nextInt();
        System.out.print("Masukkan jumlah baris matriks kedua: ");
        rowM2 = scan.nextInt();
        System.out.print("Masukkan jumlah kolom matriks kedua: ");
        columnM2 = scan.nextInt();
        if (columnM1 != rowM2) {
          System.out.println("Matriks tidak dapat dikalikan satu sama lain.\n");
        } else {
        	Matriks matriks1 = new Matriks(rowM1,columnM1);
        	Matriks matriks2 = new Matriks(rowM2,columnM2);
            double hasil[][] = new double[rowM1][columnM2];
          System.out.println("Masukkan elemen matriks pertama: ");
          for (i = 0; i < rowM1; i++) {
            for (j = 0; j < columnM1; j++) {
              matriks1.SetElmt(i, j, scan.nextDouble());
            }
          }
          System.out.println("Masukkan elemen matriks kedua: ");
          for (i = 0; i < rowM2; i++) {
            for (j = 0; j < columnM2; j++) {
            	matriks2.SetElmt(i, j, scan.nextDouble());
            }
          }
          for (i = 0; i < rowM1; i++) {
            for (j = 0; j < columnM2; j++) {
              for (k = 0; k < rowM2; k++) {
                jumlah = jumlah + matriks1.GetElmt(i, k) * matriks2.GetElmt(k, j);
              }
              hasil[i][j] = jumlah;
              jumlah = 0;
            }
          }
          System.out.println("Hasil perkalian matriks: ");
          for (i = 0; i < rowM1; i++) {
            for (j = 0; j < columnM1; j++) {
              System.out.print(hasil[i][j] + "\t");
            }
            System.out.println();
          }
        }
        System.out.println("Kembali ke dalam pilihan....");
        utama();
    }
    
    static void PerkalianConst(){
        int kolom,baris;
        double konstanta;
        int i, j;
        System.out.println("\n=== Perkalian Matriks dengan konstanta ===");
        Scanner scan = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris matriks: ");
        baris = scan.nextInt();
        System.out.print("Masukkan jumlah kolom matriks: ");
        kolom = scan.nextInt();
        System.out.print("Masukkan konstanta: ");
        konstanta = scan.nextDouble();
        Matriks matriks1 = new Matriks(baris,kolom);
        System.out.println("Masukkan elemen matriks pertama: ");
          for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
              matriks1.SetElmt(i, j, scan.nextDouble() * konstanta);
            }
          }
        
          System.out.println("Hasil perkalian matriks: ");
          for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
              System.out.print(matriks1.GetElmt(i,j) + "\t");
            }
            System.out.println();
          }
        
        System.out.println("Kembali ke dalam pilihan....");
        utama();
    }
    
    static void Transpose(){
        int i, j, m, n;
        Scanner scan = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris matriks: ");
        m = scan.nextInt();
        System.out.print("Masukkan jumlah kolom matriks: ");
        n = scan.nextInt();
        Matriks matriks1 = new Matriks(m,n);
        Matriks transpose = new Matriks(m,n);
        System.out.println("Masukkan elemen matriks: ");
        for(i = 0; i < m; i++){
          for(j = 0; j< n; j++){
        	  matriks1.SetElmt(i, j, scan.nextDouble());
          }
        }
        for(i = 0; i < m; i++){
          for(j = 0; j< n; j++){
            transpose.SetElmt(j, i, matriks1.GetElmt(i, j));
          }
        }
        System.out.println();
        System.out.println("Hasil transpose matriks: ");
        for(i = 0; i < n; i++){
          for(j = 0; j< m; j++){
            System.out.print(transpose.GetElmt(i, j) + "\t");
          }
          System.out.println();
        }
        System.out.println();
        System.out.println("Kembali ke dalam pilihan....");
        utama();
    }
    static void penjumlahanMatriks() {
        int kolom,baris;
        int i,j;
        System.out.println("\n=== penjumlahan antar matriks ===");
        Scanner scan = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris matriks: ");
        baris = scan.nextInt();
        System.out.print("Masukkan jumlah kolom matriks: ");
        kolom = scan.nextInt();
        Matriks matriks1 = new Matriks(baris,kolom);
        Matriks matriks2 = new Matriks(baris,kolom);
        System.out.println("Masukkan elemen matriks pertama: ");
          for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
              matriks1.SetElmt(i, j, scan.nextDouble());
            }
          }
          System.out.println("Masukkan elemen matriks kedua: ");
          for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
            	matriks2.SetElmt(i, j, scan.nextDouble());
            	matriks1.SetElmt(i, j, matriks1.GetElmt(i, j) + matriks2.GetElmt(i, j));
            }
          }
          System.out.println();
          for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
              System.out.print(matriks1.GetElmt(i, j) + "\t");
            }
            System.out.println();
          }
        System.out.println("Kembali ke dalam pilihan....");
        utama();
    }
    
    static void penguranganMatriks() {
    	int kolom,baris;
        int i,j;
        System.out.println("\n=== penjumlahan antar matriks ===");
        Scanner scan = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris matriks: ");
        baris = scan.nextInt();
        System.out.print("Masukkan jumlah kolom matriks: ");
        kolom = scan.nextInt();
        Matriks matriks1 = new Matriks(baris,kolom);
        Matriks matriks2 = new Matriks(baris,kolom);
        System.out.println("Masukkan elemen matriks pertama: ");
          for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
              matriks1.SetElmt(i, j, scan.nextDouble());
            }
          }
          System.out.println("Masukkan elemen matriks kedua: ");
          for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
            	matriks2.SetElmt(i, j, scan.nextDouble());
            	matriks1.SetElmt(i, j, matriks1.GetElmt(i, j) - matriks2.GetElmt(i, j));
            }
          }
          System.out.println();
          for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
              System.out.print(matriks1.GetElmt(i, j) + "\t");
            }
            System.out.println();
          }
        System.out.println("Kembali ke dalam pilihan....");
        utama();
    }
}