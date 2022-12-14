/**
 * Модуль содержащий класс умеющий парсить музыку
 */
package com.njves.demo;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.njves.demo.list.LinkedList;
import com.njves.demo.model.Track;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Singleton
 * Парсит плейлисты из папки с музыкой
 */
public class AudioExtractor {
    /**
     * Объект класса
     */
    private static AudioExtractor instance;

    /**
     * Путь к папке с музыкой
     */
    public final File LIBRARY_PATH = new File("library");
    /**
     * Путь к папке с обложками музыки
     */
    private final File COVER_PATH = new File(LIBRARY_PATH, "covers/");

    /**
     * Разделитель для путей файлов
     */
    private final static String SEPARATOR = "/";

    /**
     * Конструктор класса
     */
    private AudioExtractor() {
        initDirectories();
    }

    /**
     * Возвращает экземпляр класса
     * @return экземпляр класса
     */
    public static AudioExtractor getInstance() {
        if(instance == null) {
            instance = new AudioExtractor();
            return instance;
        }
        return instance;
    }

    /**
     * Получает трек из папки по названию
     * @param filename путь к песне
     * @return объект песни
     */
    public Track getTrack(String filename) {
        Track track = new Track();
        File file = new File(LIBRARY_PATH, filename);
        try {
            Mp3File trackFile = new Mp3File(file);
            ID3v1 tags = trackFile.getId3v1Tag();;
            if(trackFile.hasId3v2Tag()) {
                tags = trackFile.getId3v2Tag();
            }
            if(tags == null) {
                throw new RuntimeException("Ни одного тега нет");
            }
            if(tags.getArtist() == null || tags.getArtist().isEmpty()) {
                track.setArtist(getArtistWhenTagIsNullByFilename(trackFile.getFilename()));
            } else {
                track.setArtist(tags.getArtist());
            }
            if(tags.getTitle() == null || tags.getTitle().isEmpty()) {
                track.setTitle(getTitleWhenTagIsNullByFilename(trackFile.getFilename()));
            }
            else {
                track.setTitle(tags.getTitle());
            }
            track.setLength(trackFile.getLength());
            String coverLink = writeAlbumImage(trackFile);
            track.setCoverLink(coverLink);
            track.setFileLink(trackFile.getFilename());
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            System.out.println(e);
        }
        return track;
    }

    /**
     * Возвращает список треков
     * @return список тректов
     */
    public LinkedList<Track> getTracks() {
        LinkedList<Track> trackList = new LinkedList<>();
        for(File musicFile : LIBRARY_PATH.listFiles()) {
            if(musicFile.isDirectory()) {
                continue;
            }
            trackList.append(getTrack(musicFile.getName()));
        }
        return trackList;
    }

    /**
     * Создает директорию с песнями
     */
    private void initDirectories() {
        if(!LIBRARY_PATH.exists()) {
            boolean mkLibraryDir = LIBRARY_PATH.mkdir();
            if(!COVER_PATH.exists()) {
                boolean mkCoversDir = COVER_PATH.exists();
            }
        }
    }

    /**
     * Возвращает название песни из пути к директории
     * @param filename путь к песне
     * @return название песни
     */
    private String getTitleWhenTagIsNullByFilename(String filename) {
        return filename.substring(8).split("-")[1].strip();
    }

    /**
     * Возвращает исполнителя песни из пути к директории
     * @param filename путь к песне
     * @return исполнитель песни
     */
    private String getArtistWhenTagIsNullByFilename(String filename) {
        return filename.substring(8).split("-")[0].strip();
    }

    /**
     * Создает обложку песни
     * @param trackFile объект песни
     * @return путь к обложке
     */
    public String writeAlbumImage(Mp3File trackFile) {
        byte[] image = trackFile.getId3v2Tag().getAlbumImage();
        String artist = trackFile.getId3v2Tag().getArtist();
        String title = trackFile.getId3v2Tag().getTitle();
        if(image == null) {
            return getPlaceholderImage();
        }
        RandomAccessFile file;
        String mimeType = getImageTypeFromMime(trackFile.getId3v2Tag().getAlbumImageMimeType());
        String filename = composeFilename(artist + "_" + title, mimeType);
        try {
            file = new RandomAccessFile(filename, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            file.write(image);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }

    /**
     * Получает расширение файла
     * @param mimeType строка типа 'image/jpeg', 'image/png'
     * @return тип картинки
     */
    private String getImageTypeFromMime(String mimeType) {
        String[] splitMimeType = mimeType.split(SEPARATOR);
        return splitMimeType[splitMimeType.length - 1];
    }

    /**
     * Формирует название файла
     * @param filename путь к файлу
     * @param filetype рассширение файла
     * @return возвращает название файла
     */
    private String composeFilename(String filename, String filetype) {
        return COVER_PATH.getAbsolutePath() + SEPARATOR + filename + "." + filetype;
    }

    /**
     * Возвращает placeholder трека
     * @return placeholder обложки
     */
    private String getPlaceholderImage() {
        return COVER_PATH.getAbsolutePath() + SEPARATOR + "placeholder" + "." + "png";
    }

}
