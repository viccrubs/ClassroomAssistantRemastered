package nju.classroomassistant.teacher.views.discussion

import com.jfoenix.controls.JFXButton
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextArea
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.effect.Effect
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import kfoenix.jfxlistview
import kfoenix.jfxtextfield
import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.network.GlobalVariables
import tornadofx.*

class DiscussionView : View("讨论") {

    // Discussion messages list
    private val chatItems = GlobalVariables.currentDiscussionQueue

    override val root = borderpane {

        importStylesheet("/css/main.css")

        val discussionTitle = jfxtextfield {
            text = "暂无标题"
            isLabelFloat = true
            isDisable = !GlobalVariables.discussionStart

            style {
                fontSize = 20.px
                alignment = Pos.BOTTOM_CENTER
                paddingTop = 20
            }

        }

        top = discussionTitle

        center = stackpane {

            paddingTop = 50

            jfxlistview(chatItems) {
                selectionModel.selectionMode = SelectionMode.SINGLE

                // The codes below implements a bubble-like style but is ugly
//                setCellFactory {
//                    ChatCellFactory(this.scene)
//                }

                
                style {
                    effect = DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(0, 0, 0, 0.8), 10.0, 0.0, 0.0, 0.0)
                    backgroundRadius += box(5.px)
                }

            }
        }


        bottom = vbox(spacing = 20) {
            padding = Insets(30.0, 5.0, 30.0, 5.0)

            jfxbutton(value = "开始", btnType = JFXButton.ButtonType.RAISED) {
                setPrefSize(100.0, 40.0)
                // set on action here
                action {
                    GlobalVariables.discussionStart = !GlobalVariables.discussionStart
                    val isStart = GlobalVariables.discussionStart

                    if (isStart) {
                        // Clear text, disable inputting title and set new default title
                        discussionTitle.text = ""
                        discussionTitle.isDisable = false
                        text = "结束"
                        discussionTitle.text = "讨论${GlobalVariables.discussionCount}"

                        style {
                            backgroundColor += c("C74C48")
                            textFill = Color.WHITE
                            prefWidth = 300.px
                            prefHeight = 30.px
                        }
                    } else {
                        discussionTitle.isDisable = true
                        text = "开始"

                        style {
                            backgroundColor += c("5264AE")
                            textFill = Color.WHITE
                            prefWidth = 300.px
                            prefHeight = 30.px
                        }
                    }
                }

                style {
                    backgroundColor += c("5264AE")
                    textFill = Color.WHITE
                    prefWidth = 300.px
                    prefHeight = 30.px
                }
            }

            alignment = Pos.CENTER
        }

        maxWidth = 1000.0
        maxHeight = 1000.0
    }
}

class ChatCellFactory(root: Scene) : ListCell<Pair<String?, String>>() {

    override fun updateItem(value: Pair<String?, String>?, empty: Boolean) {
        if (graphic == null && value != null) {
            val textArea = TextArea()
            val grid = GridPane()
            val root = HBox()
            val nameBar = HBox()

            nameBar.alignment = Pos.TOP_LEFT
            nameBar.maxWidth = 20.0
            nameBar.add(Label(value.first))
            grid.add(nameBar, 0, 0)
            grid.add(textArea, 0, 1, 5, 1)
            root.add(nameBar)
            root.add(textArea)

//            textArea.maxWidthProperty().bind(widthProperty().minus(100))
            textArea.minHeight = 1.0
            textArea.prefHeight = 30.0
            textArea.isWrapText = true
            textArea.isEditable = false
            textArea.text = value.second
            setMine(grid)
            graphic = grid
        }
    }

    private fun setMine(hBox: GridPane) {
        hBox.alignment = Pos.BASELINE_RIGHT
        for (child in hBox.children) {
            if (child is TextArea) {
                child.style = "-fx-control-inner-background: #F5F6F8;"
                break
            }
        }
    }
}

class DiscussionItem(message: StudentSendDiscussionMessage, private val studentId: String?) {
    private val content = message.content

    override fun toString(): String {
        return "$studentId $content"
    }
}

class DiscussionController : PageController(DiscussionView::class)