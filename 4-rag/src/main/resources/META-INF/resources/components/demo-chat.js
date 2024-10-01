import {css, LitElement} from 'lit';
import '@vaadin/icon';
import '@vaadin/button';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/form-layout';
import '@vaadin/progress-bar';
import '@vaadin/checkbox';
import '@vaadin/horizontal-layout';
import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column.js';

export class DemoChat extends LitElement {

    _stripHtml(html)   {
        const div = document.createElement("div");
        div.innerHTML = html;
        return div.textContent || div.innerText || "";
    }

    connectedCallback() {
        const chatBot = document.getElementsByTagName("chat-bot")[0];

        const socket = new WebSocket("ws://" + window.location.host + "/doc-support");
        socket.onmessage = function (event) {
            chatBot.hideLastLoading();
            // LLM response
            var lastMessage;
            if (chatBot.messages.length > 0) {
                lastMessage = chatBot.messages[chatBot.messages.length - 1];
            }
            if (lastMessage && lastMessage.sender.id === "llm") {
                if (! lastMessage.msg) {
                    lastMessage.msg = "";
                }
                lastMessage.msg += event.data;
                let bubbles = chatBot.shadowRoot.querySelectorAll("chat-bubble");
                let bubble = bubbles.item(bubbles.length - 1);
                bubble.innerHTML = lastMessage.msg;
                chatBot.body.scrollTo({ top: chatBot.body.scrollHeight, behavior: 'smooth' })
            } else {
                chatBot.sendMessage(event.data, {
                    right: false,
                    continued: true,
                    sender: {
                        id: "llm"
                    }
                });
            }
        }

        const that = this;
        chatBot.addEventListener("sent", function (e) {
            if (e.detail.message.right === true) {
                // User message
                socket.send(that._stripHtml(e.detail.message.message));
                chatBot.sendMessage("", {
                    right: false,
                    loading: true
                });
            }
        });
    }


}

customElements.define('demo-chat', DemoChat);