package nju.classroomassistant.teacher.views.exercise

import com.jfoenix.controls.JFXButton
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import kfoenix.jfxbutton
import kfoenix.jfxcombobox
import kfoenix.jfxradiobutton
import tornadofx.*


class ExerciseInitView : View("练习") {
    val isBlankProperty = SimpleBooleanProperty(true)
    val choiceNumberProperty = SimpleIntegerProperty(4)

    val controller: ExerciseController by inject()

    override val root =  vbox {
            hbox {
                label("题型") {
                    textAlignment = TextAlignment.LEFT
                    prefWidth = 80.0
                }
                togglegroup {
                    jfxradiobutton("填空题", value = true)
                        .setOnAction {
                            isBlankProperty.set(true)
                        }
                    jfxradiobutton("选择题")
                        .setOnAction {
                            isBlankProperty.set(false)
                        }
                    spacing = 20.0
                }
                alignment = Pos.BASELINE_LEFT
            }

            borderpane {
                removeWhen(isBlankProperty)
                left = label("选项个数") {
                    paddingTop = 10.0
                }
                right = jfxcombobox(choiceNumberProperty, values = arrayListOf(2, 3, 4, 5, 6)) {
                    prefWidth = 150.0
                }


            }

            jfxbutton("发布", JFXButton.ButtonType.RAISED) {

                action {
                    controller.to<CollectingView>()
                }
                prefWidth = 300.0
                prefHeight = 30.0

                style {
                    backgroundColor += c("#5264AE")
                    textFill = Color.WHITE
                }
            }


            maxWidth = 300.0
            maxHeight = 300.0
            spacing = 40.0


        }

}