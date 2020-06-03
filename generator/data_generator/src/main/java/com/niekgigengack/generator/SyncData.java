package com.niekgigengack.generator;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class SyncData {
    private final String id;
    private final String status;
    private final String created;
    private String dataString0 = "";
    private String dataString1 = "";
    private String dataString2 = "";
    private String dataString3 = "";
    private String dataString4 = "";
    private String dataString5 = "";

    private final ArrayList<String> urls = new ArrayList<>();
    private final ArrayList<String> categories = new ArrayList<>();

    private static final int MISSING_PERCENTAGE = 8;
    private static final int CORRUPT_PERCENTAGE = 10;

    {
        urls.add("test");
        urls.add("42");
        urls.add("1337");
        urls.add("banana");
        urls.add("for");
        urls.add("scale");

        categories.add("TSV");
        categories.add("TXT");
        categories.add("XML");
        categories.add("JSON");
        categories.add("YAML");
        categories.add("CSV");
    }

    public SyncData() {
        this.id = UUID.randomUUID().toString();
        this.status = "0";
        this.created = getDateTime(0L);

        if (!isMissing()) { // has a certain % chance of returning true, in that case missing data is
                            // simulated for this field
            if (isCorrupted()) {
                this.dataString0 = "*";
            } else {
                this.dataString0 = generateURL();
            }
        }

        if (!isMissing()) {
            if (isCorrupted()) {
                this.dataString1 = "*";
            } else {
                this.dataString1 = getDateTime(7L);
            }
        }

        if (!isMissing()) {
            if (isCorrupted()) {
                this.dataString2 = "*";
            } else {
                this.dataString2 = categories.get(getRandomInt(6));
            }
        }

        if (!isMissing()) {
            if (isCorrupted()) {
                this.dataString3 = "*";
            } else {
                this.dataString3 = generatePostalCode();
            }
        }

        if (!isMissing()) {
            if (isCorrupted()) {
                this.dataString4 = "*";
            } else {
                this.dataString4 = Integer.toString(getRandomInt(99));
            }
        }

        if (!isMissing()) {
            if (isCorrupted()) {
                this.dataString5 = "*";
            } else {
                this.dataString5 = getRandomIntSequence();
            }
        }
    }

    private boolean isCorrupted() {
        return (int) getRandomInt(100 / CORRUPT_PERCENTAGE) == 0;
    }

    private boolean isMissing() {
        return (int) getRandomInt(100 / MISSING_PERCENTAGE) == 0;
    }

    private String getRandomIntSequence() {
        String randomIntString = "";

        for (int i = 0; i < 12; i++) {
            randomIntString += Integer.toString((int) getRandomInt(9));
        }

        return randomIntString;
    }

    private String generatePostalCode() {
        final ArrayList<Character> chars = new ArrayList<>();
        chars.add((char) (getRandomInt(9) + 48)); // get 0-9 from ascii table with offset
        chars.add((char) (getRandomInt(9) + 48));
        chars.add((char) (getRandomInt(9) + 48));
        chars.add((char) (getRandomInt(9) + 48));
        chars.add((char) (getRandomInt(27) + 65)); // get A-Z from scii table with offset
        chars.add((char) (getRandomInt(27) + 65));

        return String.format("%c%c%c%c%c%c", chars.toArray(new Character[chars.size()]));
    }

    private String generateURL() {
        return "http://" + urls.get(getRandomInt(6)) + ".com/file.html";
    }

    private String getDateTime(final long daysInPast) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now().minusDays(daysInPast);
        return dtf.format(now).replace(" ", "T") + "+00:00"; // cheating but it's a stub anyway ;)
    }

    private int getRandomInt(final int range) {
        return (int) (Math.random() * range);
    }

    // Getters and Setters
    public String getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public String getCreated() {
        return this.created;
    }

    public String getDataString0() {
        return this.dataString0;
    }

    public String getDataString1() {
        return this.dataString1;
    }

    public String getDataString2() {
        return this.dataString2;
    }

    public String getDataString3() {
        return this.dataString3;
    }

    public String getDataString4() {
        return this.dataString4;
    }

    public String getDataString5() {
        return this.dataString5;
    }
}