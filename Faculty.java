public enum Faculty {
    EEE("School of Electrical and Electronic Engineering"),
    SCSE("School of Computer Science and Engineering"),
    ADM("School of Arts,Design and Media"),
    NBS ("Nayang Business School"),
    SSS("School of Social Sciences"),
    OTHER("Other Faculty");
    
  private String description;

    Faculty(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // You can add more faculties here


}

