package iş203;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Ders {
    String dersKodu;
    String dersAd;
    String dersDonem;
    String ogretimGorevi;
    public Ders(String dersKodu, String dersAd, String dersDonem, String ogretimGorevi) {
        this.dersKodu = dersKodu;
        this.dersAd = dersAd;
        this.dersDonem = dersDonem;
        this.ogretimGorevi = ogretimGorevi;
    }
}

class Ogrenci {
    String ogrenciNo;
    String ogrenciAd;
    String ogrenciSoyad;
    String ogrenciBolum;
    List<Ders> ogrenciDersler = new ArrayList<>();
    public Ogrenci(String ogrenciNo, String ogrenciAd, String ogrenciSoyad, String ogrenciBolum) {
        this.ogrenciNo = ogrenciNo;
        this.ogrenciAd = ogrenciAd;
        this.ogrenciSoyad = ogrenciSoyad;
        this.ogrenciBolum = ogrenciBolum;
    }
}

class OgretimGorevlisi {
    String ogretmenNo;
    String ad;
    String soyad;
    String bolum;
    List<Ders> verdigiDersler = new ArrayList<>();
    public OgretimGorevlisi(String ogretmenNo, String ad, String soyad, String bolum) {
        this.ogretmenNo = ogretmenNo;
        this.ad = ad;
        this.soyad = soyad;
        this.bolum = bolum;
    }
}

public class Uygulama {
    private static final String DERS_CSV_FILE = "dersler.csv";
    private static final String OGRENCI_CSV_FILE = "ogrenciler.csv";
    private static final String OGRETIMGOREVLISI_CSV_FILE = "ogretimgorevlileri.csv";
    
    private static void writeToCSV(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Uygulama");

            JButton dersFormButton = new JButton("Ders Formu");
            JButton ogrenciFormButton = new JButton("Öğrenci Formu");
            JButton ogretimGorevlisiFormButton = new JButton("Öğretim Görevlisi Formu");

            List<Ders> dersListesi = new ArrayList<>();
            List<Ogrenci> ogrenciListesi = new ArrayList<>();
            List<OgretimGorevlisi> ogretimGorevlisiListesi = new ArrayList<>();

            
            dersFormButton.addActionListener(e -> {
                JFrame dersFrame = new JFrame("Ders Formu");

                JTextField dersKoduField = new JTextField();
                JTextField dersAdField = new JTextField();
                JTextField dersDonemField = new JTextField();
                JTextField ogretimGoreviField = new JTextField();

                JButton kaydetButton = new JButton("Kaydet");

                kaydetButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Ders ders = new Ders(dersKoduField.getText(), dersAdField.getText(), dersDonemField.getText(), ogretimGoreviField.getText());

                        dersListesi.add(ders);
                        writeToCSV(DERS_CSV_FILE, String.format("%s,%s,%s,%s", ders.dersKodu, ders.dersAd, ders.dersDonem, ders.ogretimGorevi));
                    }
                });

                JPanel panel = new JPanel(new GridLayout(5, 2));
                panel.add(new JLabel("Ders Kodu:"));
                panel.add(dersKoduField);
                panel.add(new JLabel("Ders Adı:"));
                panel.add(dersAdField);
                panel.add(new JLabel("Ders Dönemi:"));
                panel.add(dersDonemField);
                panel.add(new JLabel("Öğretim Görevlisi:"));
                panel.add(ogretimGoreviField);
                panel.add(new JLabel(""));
                panel.add(kaydetButton);

                dersFrame.add(panel);
                dersFrame.setSize(500, 500);
                dersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dersFrame.setVisible(true);
            });

            
            ogrenciFormButton.addActionListener(e -> {
                JFrame ogrenciFrame = new JFrame("Öğrenci Formu");

                JTextField ogrenciNoField = new JTextField();
                JTextField ogrenciAdField = new JTextField();
                JTextField ogrenciSoyadField = new JTextField();
                JTextField ogrenciBolumField = new JTextField();
                JComboBox<String> derslerComboBox = new JComboBox<>();

                dersListesi.forEach(ders -> derslerComboBox.addItem(ders.dersAd));

                JButton kaydetButton = new JButton("Kaydet");

                kaydetButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Ogrenci ogrenci = new Ogrenci(ogrenciNoField.getText(), ogrenciAdField.getText(), ogrenciSoyadField.getText(), ogrenciBolumField.getText());
                        Ders seciliDers = dersListesi.get(derslerComboBox.getSelectedIndex());
                        ogrenci.ogrenciDersler.add(seciliDers);

                        ogrenciListesi.add(ogrenci);

                        writeToCSV(OGRENCI_CSV_FILE, String.format("%s,%s,%s,%s,%s", ogrenci.ogrenciNo, ogrenci.ogrenciAd, ogrenci.ogrenciSoyad, ogrenci.ogrenciBolum, seciliDers.dersKodu));
                    }
                });

                JPanel panel = new JPanel(new GridLayout(6, 2));
                panel.add(new JLabel("Öğrenci No:"));
                panel.add(ogrenciNoField);
                panel.add(new JLabel("Öğrenci Adı:"));
                panel.add(ogrenciAdField);
                panel.add(new JLabel("Öğrenci Soyadı:"));
                panel.add(ogrenciSoyadField);
                panel.add(new JLabel("Bölüm:"));
                panel.add(ogrenciBolumField);
                panel.add(new JLabel("Aldığı Ders:"));
                panel.add(derslerComboBox);
                panel.add(new JLabel(""));
                panel.add(kaydetButton);

                ogrenciFrame.add(panel);
                ogrenciFrame.setSize(500, 500);
                ogrenciFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ogrenciFrame.setVisible(true);
            });

            ogretimGorevlisiFormButton.addActionListener(e -> {
                JFrame ogretimGorevlisiFrame = new JFrame("Öğretim Görevlisi Formu");

                JTextField ogretmenNoField = new JTextField();
                JTextField adField = new JTextField();
                JTextField soyadField = new JTextField();
                JTextField bolumField = new JTextField();
                JComboBox<String> verdigiDerslerComboBox = new JComboBox<>();

                dersListesi.forEach(ders -> verdigiDerslerComboBox.addItem(ders.dersAd));

                JButton kaydetButton = new JButton("Kaydet");

                kaydetButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        OgretimGorevlisi ogretimGorevlisi = new OgretimGorevlisi(ogretmenNoField.getText(), adField.getText(), soyadField.getText(), bolumField.getText());
                        Ders seciliDers = dersListesi.get(verdigiDerslerComboBox.getSelectedIndex());
                        ogretimGorevlisi.verdigiDersler.add(seciliDers);

                        ogretimGorevlisiListesi.add(ogretimGorevlisi);

                        writeToCSV(OGRETIMGOREVLISI_CSV_FILE, String.format("%s,%s,%s,%s,%s", ogretimGorevlisi.ogretmenNo, ogretimGorevlisi.ad, ogretimGorevlisi.soyad, ogretimGorevlisi.bolum, seciliDers.dersKodu));
                    }
                });

                JPanel panel = new JPanel(new GridLayout(6, 2));
                panel.add(new JLabel("Öğretmen No:"));
                panel.add(ogretmenNoField);
                panel.add(new JLabel("Ad:"));
                panel.add(adField);
                panel.add(new JLabel("Soyad:"));
                panel.add(soyadField);
                panel.add(new JLabel("Bölüm:"));
                panel.add(bolumField);
                panel.add(new JLabel("Verdiği Dersler:"));
                panel.add(verdigiDerslerComboBox);
                panel.add(new JLabel(""));
                panel.add(kaydetButton);

                ogretimGorevlisiFrame.add(panel);
                ogretimGorevlisiFrame.setSize(500, 500);
                ogretimGorevlisiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ogretimGorevlisiFrame.setVisible(true);
            });

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 1));
            panel.add(dersFormButton);
            panel.add(ogrenciFormButton);
            panel.add(ogretimGorevlisiFormButton);

            frame.add(panel);
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

