package com.partymaker.mvc.model.whole;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.persistence.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "event", schema = "partymaker2")
public class event implements Serializable {

    @Id
    @Column(name = "id_event")
    @GeneratedValue
    private int id_event;

    @Column(name = "club_name")
    private String club_name;
    @Column(name = "date")
    private String date;
    @Column(name = "time", nullable = true, length = 45)
    private String time;
    @Column(name = "location")
    private String location;
    @Column(name = "club_capacity", nullable = true, length = 45)
    private String club_capacity;
    @Column(name = "party_name")
    private String party_name;
    @Column(name = "zip_code")
    private String zip_code;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<BottleEntity> bottles = new ArrayList<>();

    @OneToMany(mappedBy = "eventEntity", cascade = CascadeType.ALL)
    private List<TicketEntity> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "eventEntity", cascade = CascadeType.ALL)
    private List<TableEntity> tables = new ArrayList<>();

    @OneToMany(mappedBy = "eventEntity", cascade = CascadeType.ALL)
    private List<PhotoEntity> photos = new ArrayList<>();

    @Transient
    private List<MultipartFile> images;

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getClub_capacity() {
        return club_capacity;
    }

    public void setClub_capacity(String club_capacity) {
        this.club_capacity = club_capacity;
    }


    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }


    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public List<BottleEntity> getBottles() {
        return bottles;
    }

    public void setBottles(List<BottleEntity> bottles) {
        this.bottles = bottles;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public List<TableEntity> getTables() {
        return tables;
    }

    public void setTables(List<TableEntity> tables) {
        this.tables = tables;
    }

    public List<PhotoEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoEntity> photos) {
        this.photos = photos;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        event that = (event) o;

        if (id_event != that.id_event) return false;
        if (club_name != null ? !club_name.equals(that.club_name) : that.club_name != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (club_capacity != null ? !club_capacity.equals(that.club_capacity) : that.club_capacity != null)
            return false;
        if (party_name != null ? !party_name.equals(that.party_name) : that.party_name != null) return false;
        if (zip_code != null ? !zip_code.equals(that.zip_code) : that.zip_code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id_event;
        result = 31 * result + (club_name != null ? club_name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (club_capacity != null ? club_capacity.hashCode() : 0);
        result = 31 * result + (party_name != null ? party_name.hashCode() : 0);
        result = 31 * result + (zip_code != null ? zip_code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("event{");
        sb.append("id_event=").append(id_event);
        sb.append(", club_name='").append(club_name).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", club_capacity='").append(club_capacity).append('\'');
        sb.append(", party_name='").append(party_name).append('\'');
        sb.append(", zip_code='").append(zip_code).append('\'');
        sb.append('}');
        return sb.toString();
    }


    public static void main(String[] args) throws IOException {
        /*
         * 1. How to convert an image file to  byte array?
    	 */

        File file = new File("/home/anton/863946db_o.jpeg");

        FileInputStream fis = new FileInputStream(file);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        }

        byte[] bytes = bos.toByteArray();


        String butesString = Arrays.toString(bytes);

        System.out.println(butesString);

        String[] byteValues = butesString.substring(1, butesString.length() - 1).split(",");
        bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        /*
         * 2. How to convert byte array back to an image file?
         */

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpeg");

        //ImageIO is a class containing static methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding.

        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();

        Image image = reader.read(0, param);
        //got an image file

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //bufferedImage is the RenderedImage to be written

        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);

        File imageFile = new File("/home/anton/deploy/myimage.jpeg");
        ImageIO.write(bufferedImage, "jpeg", imageFile);

        System.out.println(imageFile.getAbsolutePath());

    }
}
