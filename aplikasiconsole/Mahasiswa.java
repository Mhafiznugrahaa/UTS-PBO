package aplikasiconsole;
// import java.util.Scanner;

public class Mahasiswa {
    private final String nim;
    private final String nama;

    // Constructor
    public Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }

    // Getter Methods
    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    // Untuk menampilkan data mahasiswa secara rapi (opsional)
    @Override
    public String toString() {
        return "NIM: " + nim + " | Nama: " + nama;
    }
}