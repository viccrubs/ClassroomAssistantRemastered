package nju.classroomassistant.teacher.network

import tornadofx.*
import kotlin.properties.Delegates

/**
 * Author: J.D. Liao
 * Date: 2019-06-10
 * Description:
 */
object GlobalVariables {

    var discussionStart: Boolean by Delegates.observable(false) { _, _, newValue ->
        // Send ending message to student clients when quit discussion
        if (!newValue) {
            Thread { sendDiscussionEndingMessage() }.start()
        } else {
            Thread { sendDiscussionStartMessage() }.start()
            currentDiscussionQueue.clear()
        }
    }

    val currentDiscussionQueue = ArrayList<String>().observable()

    private fun sendDiscussionEndingMessage() {
        for (s in Server.studentMap.allStudents) {
            s.handler.sendDiscussionEndMessage()
        }
    }

    private fun sendDiscussionStartMessage() {
        for (s in Server.studentMap.allStudents) {
            s.handler.sendDiscussionStartMessage()
        }
    }
}