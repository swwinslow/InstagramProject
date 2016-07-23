package edu.bsu.cs222.instagram;

import java.io.IOException;
import org.json.JSONException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UI extends Application {
	
	public static void main(String[] args) throws IOException {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Insta-Friend");
		Group root = new Group();
		Scene scene = new Scene(root, 900, 700, Color.color(.22, .39, .53));
		BorderPane appLayout = buildAppLayout(scene);
		root.getChildren().add(appLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private BorderPane buildAppLayout(Scene scene) {
		BorderPane appLayout = new BorderPane();
		appLayout.prefWidthProperty().bind(scene.widthProperty());
		appLayout.prefHeightProperty().bind(scene.heightProperty());
		Label welcomeLabel = buildWelcomeLabel();
		appLayout.setTop(welcomeLabel);
		BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
		TabPane tabs = buildTabs(appLayout);
		appLayout.setCenter(tabs);
		setupButtonHandlers(tabs);
		return appLayout;
	}

	private Label buildWelcomeLabel() {
		Label welcomeLabel = new Label("Welcome to Insta-Friend!");
		welcomeLabel.setFont(Font.font("Pilgi", FontWeight.EXTRA_BOLD, 60));
		welcomeLabel.setTextFill(Color.WHITE);
		welcomeLabel.setCenterShape(true);
		welcomeLabel.setPadding(new Insets(10, 10, 10, 10));
		return welcomeLabel;
	}

	private TabPane buildTabs(BorderPane appLayout) {
		TabPane tabs = new TabPane();
		tabs.setPadding(new Insets(0, 10, 10, 10));
		tabs.prefWidthProperty().bind(appLayout.widthProperty());
		tabs.prefHeightProperty().bind(appLayout.heightProperty());
		tabs.getTabs().add(buildSearchTab());
		return tabs;
	}

	private Tab buildSearchTab() {
		Tab searchTab = new Tab("Search Center");
		searchTab.setClosable(false);
		searchTab.setContent(buildSearchTabLayout());
		return searchTab;
	}

	private BorderPane buildSearchTabLayout() {
		BorderPane searchTabLayout = new BorderPane();
		Label explanationLabel = new Label(
				"To compare two Instagram Users, type in a name, hit search, and \nselect a user by clicking on their username.\nRepeat the process for the other side/user, and hit 'compare!'.");
		explanationLabel.setPadding(new Insets(10, 0, 0, 0));
		explanationLabel.setTextFill(Color.WHITE);
		searchTabLayout.setTop(explanationLabel);
		searchTabLayout.setLeft(buildSearchResultsLayout(searchTabLayout));
		searchTabLayout.setRight(buildSearchResultsLayout(searchTabLayout));
		searchTabLayout.setBottom(buildCompareButton());
		return searchTabLayout;
	}

	private Button buildCompareButton() {
		Button compareButton = new Button("Compare!");
		compareButton.setFont(Font.font("Verdana", 20));
		compareButton.setPadding(new Insets(10, 10, 10, 10));
		BorderPane.setAlignment(compareButton, Pos.CENTER);
		BorderPane.setMargin(compareButton, new Insets(10, 10, 0, 10));
		return compareButton;
	}

	private BorderPane buildSearchResultsLayout(BorderPane searchTabLayout) {
		BorderPane searchResultsLayout = new BorderPane();
		searchResultsLayout.prefWidthProperty().bind(searchTabLayout.widthProperty().divide(2).subtract(5));
		searchResultsLayout.prefHeightProperty().bind(searchTabLayout.heightProperty());
		TableView<InstagramUser> resultsTable = buildUserTable("Please enter a search above.");
		resultsTable.prefWidthProperty().bind(searchResultsLayout.widthProperty());
		resultsTable.prefHeightProperty().bind(searchResultsLayout.heightProperty());
		searchResultsLayout.setTop(buildSearchBar());		
		searchResultsLayout.setCenter(resultsTable);
		return searchResultsLayout;
	}

	private HBox buildSearchBar() {
		TextField searchInput = buildSearchInputField();
		Button searchButton = buildSearchButton();
		HBox searchBar = new HBox();
		searchBar.getChildren().addAll(searchInput, searchButton);
		return searchBar;
	}

	private TextField buildSearchInputField() {
		TextField searchInput = new TextField();
		searchInput.setPromptText("Enter first Instagram User");
		searchInput.setPadding(new Insets(10, 10, 10, 10));
		searchInput.setPrefWidth(200);
		HBox.setMargin(searchInput, new Insets(10, 0, 0, 0));
		return searchInput;
	}

	private Button buildSearchButton() {
		Button searchButton = new Button("Search");
		searchButton.setPadding(new Insets(10, 10, 10, 10));
		HBox.setMargin(searchButton, new Insets(10, 10, 10, 10));
		return searchButton;
	}

	@SuppressWarnings("unchecked")
	private TableView<InstagramUser> buildUserTable(String emptyTableMessage) {
		TableView<InstagramUser> table = new TableView<InstagramUser>();
		TableColumn<InstagramUser, String> usernameColumn = buildColumn("Username", "username");
		TableColumn<InstagramUser, String> fullnameColumn = buildColumn("Full Name", "userFullname");
		TableColumn<InstagramUser, String> profilePictureColumn = buildColumn("Pic", "profilePictureURL");
		usernameColumn.prefWidthProperty().bind(table.prefWidthProperty().subtract(85).divide(2));		
		fullnameColumn.prefWidthProperty().bind(table.prefWidthProperty().subtract(85).divide(2));
		profilePictureColumn.setPrefWidth(85);
		profilePictureColumn.setSortable(false);
		configureProfilePictureColumn(profilePictureColumn);		
		table.getColumns().addAll(profilePictureColumn, usernameColumn, fullnameColumn);
		table.setPlaceholder(new Label(emptyTableMessage));
		return table;
	}
	
	private TableColumn<InstagramUser, String> buildColumn(String columnTitle, String userAttribute){
		TableColumn<InstagramUser, String> newColumn = new TableColumn<InstagramUser, String>(columnTitle);
		newColumn.setCellValueFactory(new PropertyValueFactory<InstagramUser, String>(userAttribute));
		return newColumn;
	}
	
	private void configureProfilePictureColumn(TableColumn<InstagramUser, String> column){
		column.setCellFactory(new Callback<TableColumn<InstagramUser, String>, TableCell<InstagramUser, String>>(){
			@Override
			public TableCell<InstagramUser, String> call(TableColumn<InstagramUser, String> param) {
				TableCell<InstagramUser, String> cell = new TableCell<InstagramUser, String>(){
					@Override
					public void updateItem(String url, boolean empty){
						setProfilePicture(url, this);
					}
				};
				return cell;
			}
		});
	}
	
	private void setProfilePicture(String url,TableCell<InstagramUser, String> cell){
		//display generic picture if profile picture is empty (private user)
		if (url!=null){
			ImageView pic = new ImageView(url);
			updateBlankImage(pic);
			int fixedPictureWidth = 75;
			pic.setFitWidth(fixedPictureWidth);
			pic.setPreserveRatio(true);
			cell.setGraphic(pic);
		}else{
			cell.setGraphic(new ImageView());
		}		
	}
	
	private void updateBlankImage(ImageView image){
		if (image.getImage().getWidth()==0){
			Image genericProfilePicture = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("genericProfilePicture.JPG"));
			image.setImage(genericProfilePicture);
		}
	}

	private void setupButtonHandlers(TabPane tabs) {
		configureSearchButtonHandler(getLeftSearchResultLayout(tabs));
		configureSearchButtonHandler(getRightSearchResultLayout(tabs));
		configureCompareButtonHandler(tabs);
	}

	private void configureSearchButtonHandler(BorderPane layout) {
		TableView<InstagramUser> table = getSearchOutputTable(layout);
		getSearchButton(layout).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String inputText = getSearchInputText(layout);
				if (!inputText.contentEquals("")) {
					try {
						InstagramUserList searchResults = new InstagramUserListBuilder().createSearchResultsList(inputText);
						ObservableList<InstagramUser> newUsers = FXCollections.observableList(searchResults.getUsers());
						table.setItems(newUsers);
					} catch (IOException | JSONException e1) {
						table.setPlaceholder(new Label("Please check network connection"));
					}
				}else{
					table.setPlaceholder(new Label("Please enter a search above."));
					table.getItems().removeAll(table.getItems());
				}
			}
		});
	}

	private BorderPane getLeftSearchResultLayout(TabPane tabs) {
		return ((BorderPane) getSearchTabLayout(tabs).getLeft());
	}

	private BorderPane getRightSearchResultLayout(TabPane tabs) {
		return ((BorderPane) getSearchTabLayout(tabs).getRight());
	}

	private BorderPane getSearchTabLayout(TabPane tabs) {
		return ((BorderPane) getSearchTab(tabs).getContent());
	}

	private Tab getSearchTab(TabPane tabs) {
		return tabs.getTabs().get(0);
	}

	private HBox getSearchBar(BorderPane layout) {
		return (HBox) layout.getTop();
	}

	private Button getSearchButton(BorderPane layout) {
		return (Button) getSearchBar(layout).getChildren().get(1);
	}

	private String getSearchInputText(BorderPane layout) {
		String input = getSearchInputField(layout).getText();
		return input;
	}

	private TextField getSearchInputField(BorderPane layout) {
		return ((TextField) getSearchBar(layout).getChildren().get(0));
	}

	@SuppressWarnings("unchecked")
	private TableView<InstagramUser> getSearchOutputTable(BorderPane layout) {
		return (TableView<InstagramUser>) layout.getCenter();
	}

	private void configureCompareButtonHandler(TabPane tabs) {
		getCompareButton(tabs).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					buildComparisonTab(tabs);
				} catch (IOException | JSONException e1) {
				}
			}
		});
	}

	private Button getCompareButton(TabPane tabs) {
		return (Button) getSearchTabLayout(tabs).getBottom();
	}

	private void buildComparisonTab(TabPane tabs) throws IOException, JSONException {
		InstagramUser leftUser = getLeftUser(tabs);
		InstagramUser rightUser = getRightUser(tabs);
		if (selectionsExist(leftUser, rightUser)) {
			tabs.getTabs().add(buildNewComparisonTab(leftUser, rightUser));
			tabs.getSelectionModel().selectLast();
			return;
		}
	}

	private InstagramUser getLeftUser(TabPane tabs) {
		return getSelection(getSearchOutputTable(getLeftSearchResultLayout(tabs)));
	}

	private InstagramUser getRightUser(TabPane tabs) {
		return getSelection(getSearchOutputTable(getRightSearchResultLayout(tabs)));
	}

	private InstagramUser getSelection(TableView<InstagramUser> table) {
		return table.getSelectionModel().getSelectedItem();
	}

	private boolean selectionsExist(InstagramUser leftSelection, InstagramUser rightSelection) {
		return rightSelection != null && leftSelection != null;
	}

	private Tab buildNewComparisonTab(InstagramUser leftUser, InstagramUser rightUser)
			throws IOException, JSONException {
		Tab newTab = new Tab(getUserName(leftUser) + " and " + getUserName(rightUser));
		newTab.setContent(buildComparisonTabLayout(leftUser, rightUser));
		return newTab;
	}

	private BorderPane buildComparisonTabLayout(InstagramUser leftUser, InstagramUser rightUser)
			throws IOException, JSONException {
		BorderPane comparisonTabLayout = new BorderPane();
		TableView<InstagramUser> comparisonOutputTable = buildUserTable(
				"These two users do not follow any of the same Instagram users.\n To compare other users, please go to the search page.\n Thank you for using Insta-Friend!");
		comparisonOutputTable.prefWidthProperty().bind(comparisonTabLayout.widthProperty());
		InstagramUserList users = getComparisonResults(leftUser, rightUser);
		ObservableList<InstagramUser> finalUsers = FXCollections.observableList(users.getUsers());
		comparisonOutputTable.setItems(finalUsers);
		comparisonTabLayout.setTop(buildComparisonSummaryLabel(//
				getUserName(leftUser) + " (who follows " + leftUser.getUserFollowsCount() + " users) and\n"//
				+ getUserName(rightUser) + " (who follows " + rightUser.getUserFollowsCount() + " users)\n"// 
				+ "follow " + users.size() + " of the same Instagram users."));
		comparisonTabLayout.setCenterShape(true);
		comparisonTabLayout.setCenter(comparisonOutputTable);
		return comparisonTabLayout;
	}

	private InstagramUserList getComparisonResults(InstagramUser leftUser, InstagramUser rightUser)
			throws IOException, JSONException {
		return new InstagramUserListBuilder().createCommonFollowsList(leftUser.getUserID(), rightUser.getUserID());
	}

	private Label buildComparisonSummaryLabel(String labelContent) {
		Label usersSummaryLabel = new Label(labelContent);
		usersSummaryLabel.setFont(Font.font("Verdana", 20));
		usersSummaryLabel.setTextFill(Color.WHITE);
		usersSummaryLabel.setPadding(new Insets(10, 10, 10, 10));
		return usersSummaryLabel;
	}

	private String getUserName(InstagramUser user) {
		return user.getUserFullname();
	}
}