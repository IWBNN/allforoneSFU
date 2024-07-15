import React, { useState, useEffect, useRef } from 'react';

const TextChatComponent: React.FC = () => {
    const [messages, setMessages] = useState<string[]>([]);
    const [input, setInput] = useState<string>('');
    const ws = useRef<WebSocket | null>(null);

    useEffect(() => {
        ws.current = new WebSocket('wss://suportscore.site/chat');
        ws.current.onmessage = (event) => {
            const newMessage = event.data;
            setMessages(prevMessages => [...prevMessages, newMessage]);
        };
        return () => {
            if (ws.current) ws.current.close();
        };
    }, []);

    const sendMessage = () => {
        if (ws.current && input.trim()) {
            ws.current.send(input);
            setInput('');
        }
    };

    return (
        <div>
            <h1>Text Chat</h1>
            <div>
                {messages.map((message, index) => (
                    <div key={index}>{message}</div>
                ))}
            </div>
            <input
                type="text"
                value={input}
                onChange={(e) => setInput(e.target.value)}
            />
            <button onClick={sendMessage}>Send</button>
        </div>
    );
};

export default TextChatComponent;
