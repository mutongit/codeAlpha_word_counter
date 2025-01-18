package com.codealpha.codealpha;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Objects;

public class WordCounterController {

    @FXML
    private Label charecterCount;

    @FXML
    private Label charecterLabel;

    @FXML
    private ComboBox<String> countChooser;

    @FXML
    private Label limitID;

    @FXML
    private TextField limitValue;

    @FXML
    private CheckBox specialCharecter;

    @FXML
    private CheckBox stopWordCheck;

    @FXML
    private CheckBox whitespaceCharecter;

    @FXML
    private Label settings;

    @FXML
    private Label wordCounter;

    @FXML
    private Label wordLabel;

    @FXML
    private TextArea wordPool;


    @FXML
    void initialize(){

        limitValue.setText("100");
        countChooser.getItems().addAll("Non","Word","Charecter");
        if(countChooser.getValue()==null){
            countChooser.setValue("Non");
            limitValue.setOpacity(0);
            limitID.setOpacity(0);
        }

        combiner();
        wordCounter();
        wordCounter.setText("0");
    }



    @FXML
    void charecterEntered(KeyEvent event) {
        combiner();
        wordCounter();

    }

    public void onSelect(ActionEvent actionEvent) {
        if(countChooser.getValue().equals("Charecter")){
            limitValue.setOpacity(1);
            limitID.setOpacity(1);
            limitValue.setText("0");
        }else if((countChooser.getValue().equals("Word"))){
            limitValue.setOpacity(1);
            limitID.setOpacity(1);
            limitValue.setText("0");
        }else{
            limitValue.setOpacity(0);
            limitID.setOpacity(0);
        }
    }

    @FXML
    void onSpecialCharecter(ActionEvent event) {
        combiner();
        wordCounter();
    }

    @FXML
    void onStopWord(ActionEvent event) {
        wordCounter();
    }

    @FXML
    void onWhiteSpace(ActionEvent event) {
        combiner();
        wordCounter();
    }



    private void combiner(){
       char[] e= wordPool.getText().toCharArray();
       String specialCharecters = "~!@#$%^&*()`[]{}\\|'\"?/><,.-=_+";
       String whiteSpaceCharecters = " \n\t";
       int whitespaceCounter = 0;
       int specialCounter = 0;
       int counter = 0;

       if(whitespaceCharecter.isSelected() && specialCharecter.isSelected()){
           charecterCount.setText(String.valueOf(wordPool.getText().length()));
       }else if(!whitespaceCharecter.isSelected() && specialCharecter.isSelected()){
           for (char c : e) {
               if (whiteSpaceCharecters.contains(String.valueOf(c))) {
                   whitespaceCounter++;
               }
               charecterCount.setText(String.valueOf(wordPool.getText().length() - whitespaceCounter));
           }
       }else if(whitespaceCharecter.isSelected() && !specialCharecter.isSelected()){
           for (char c : e) {
               if (specialCharecters.contains(String.valueOf(c))) {
                   specialCounter++;
               }
               charecterCount.setText(String.valueOf(wordPool.getText().length() - specialCounter));
           }
       }else{
           for (char c : e) {
               if ((specialCharecters + whiteSpaceCharecters).contains(String.valueOf(c))) {
                   counter++;
               }
               charecterCount.setText(String.valueOf(wordPool.getText().length() - counter));
           }
       }


       if(Integer.parseInt(charecterCount.getText())>=Integer.parseInt(limitValue.getText()) && countChooser.getValue().equals("Charecter")){
           wordPool.setEditable(false);
           wordPool.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
               if (Objects.requireNonNull(event.getCode()) == KeyCode.BACK_SPACE) {
                   if (!wordPool.isEditable()) {
                       String text = wordPool.getText();
                       if (text.length() >= Integer.parseInt(charecterCount.getText())) {
                           wordPool.setText(text);
                           wordPool.positionCaret(wordPool.getLength());
                           wordPool.setEditable(true);
                       }
                   }
               }
           });

       }else{
           wordPool.setEditable(true);
       }
    }

    private void wordCounter() {
        if (stopWordCheck.isSelected()) {
            String paragraph = wordPool.getText();

            // Remove any leading or trailing whitespace
            paragraph = paragraph.trim();

            // Check if the paragraph is empty
            if (paragraph.isEmpty()) {
                wordCounter.setText("0");
            } else {
                // Split the paragraph into words based on spaces and punctuation
                String[] words = paragraph.split("\\s+");
                int wordCount = words.length;

                wordCounter.setText(String.valueOf(wordCount));
            }
        }else{
            String[] stopWords = { "a", "an", "the", "and", "but", "or", "nor", "yet", "so", "in", "on", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "over", "under", "I", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "is", "am", "are", "was", "were", "be", "being", "been", "have", "has", "had", "do", "does", "did", "will", "would", "shall", "should", "can", "could", "may", "might", "must", "how", "when", "where", "why", "here", "there", "everywhere", "not", "no", "yes", "only", "now", "then", "very", "of", "if"};
            String paragraph = wordPool.getText();
            int counter = 0;
            int wordCount;
            // Remove any leading or trailing whitespace
            paragraph = paragraph.trim();

            String[] words = paragraph.split("\\s+");
            for(String w : words ){
                for(String z : stopWords){
                    if(w.equals(z))
                        counter++;
                }
            }
            wordCount = words.length - counter;
            wordCounter.setText(String.valueOf(wordCount));

            if(Integer.parseInt(wordCounter.getText())>=Integer.parseInt(limitValue.getText())+1 && countChooser.getValue().equals("Word")){
                wordPool.setEditable(false);
                wordPool.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    String text = wordPool.getText();
                    if(!wordPool.isEditable()) {
                        wordPool.setText(text);
                        wordPool.positionCaret(wordPool.getLength());
                    }
                    if (Objects.requireNonNull(event.getCode()) == KeyCode.BACK_SPACE) {
                        if (!wordPool.isEditable()) {

                            if (text.length() >= Integer.parseInt(charecterCount.getText())) {
                                wordPool.setText(text);
                                wordPool.positionCaret(wordPool.getLength());
                                wordPool.setEditable(true);
                            }
                        }
                    }
                });

            }else{
                wordPool.setEditable(true);
            }
        }
    }

}
