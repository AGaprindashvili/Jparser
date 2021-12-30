package Home;

import Parser.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private TextArea TextBrowser;
    @FXML
    private TextField UrlText;
    @FXML
    private ComboBox TypeSelector;
    @FXML
    private TextField TagText;
    @FXML
    private Button TagCloseBtn;

    @FXML
    public void onParserBtnClick(ActionEvent actionEvent) {
        ParserManager parser = new ParserManager();
        switch ((String)TypeSelector.getValue()) {
            case "Links":
                TextBrowser.setText(parser.getLinks(UrlText.getText()));
                break;
            case "Images":
                TextBrowser.setText(parser.getImages(UrlText.getText()));
                break;
            case "Html":
                TextBrowser.setText(parser.getHtml(UrlText.getText()));
                break;
            case "ById":
                TextBrowser.setText(parser.getHtml(UrlText.getText(), "#" + TagText.getText()));
                break;
            case "ByClass":
                TextBrowser.setText(parser.getHtml(UrlText.getText(), "." + TagText.getText()));
                break;
            case "ByTag":
                TextBrowser.setText(parser.getHtml(UrlText.getText(), TagText.getText()));
                break;
        }
    }

    @FXML
    public void onSaveBtnClick(ActionEvent actionEvent) {
        ParserManager parser = new ParserManager();
        FilesManager fl = new FilesManager();
        boolean hasFolder = fl.createFolder();
        boolean step = false;
        String type = (String)TypeSelector.getValue();
        String fileName = java.util.UUID.randomUUID().toString();

        if (hasFolder && type.equals("Links")) {
            step = fl.createFile(parser.getLinks(UrlText.getText()), fileName, "txt");
        }
        else if (hasFolder && type.equals("Images")) {
            step = fl.getFromUrl(parser.getImagesList(UrlText.getText()));
        }
        else if (hasFolder && type.equals("Html")) {
            step = fl.createFile(parser.getHtml(UrlText.getText()), fileName, "html");
        }
        else if (hasFolder && type.equals("ById")) {
            step = fl.createFile(parser.getHtml(UrlText.getText(), "#" + TagText.getText()), fileName, "txt");
        }
        else if (hasFolder && type.equals("ByClass")) {
            step = fl.createFile(parser.getHtml(UrlText.getText(), "." + TagText.getText()), fileName, "txt");
        }
        else if (hasFolder && type.equals("ByTag")) {
            step = fl.createFile(parser.getHtml(UrlText.getText(), TagText.getText()), fileName, "txt");
        }
        else {
            ResourceBundle texts = ResourceBundle.getBundle("Texts");
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle(texts.getString("oprErr"));
            msg.setHeaderText(texts.getString("folderErr"));
            msg.setContentText(texts.getString("downloadErr"));
            msg.showAndWait();
        }
    }

    @FXML
    public void onTypeSelected(ActionEvent actionEvent) {
        String type = (String)TypeSelector.getValue();
        if (type.equals("ById") || type.equals("ByClass") || type.equals("ByTag")) {
            TypeSelector.setVisible(false);
            TagText.setVisible(true);
            TagCloseBtn.setVisible(true);
            TagText.setText(type + "_Value");
        }
    }

    @FXML
    public void onTagTextClosed(ActionEvent actionEvent) {
        TypeSelector.setVisible(true);
        TagText.setVisible(false);
        TagCloseBtn.setVisible(false);
    }

}