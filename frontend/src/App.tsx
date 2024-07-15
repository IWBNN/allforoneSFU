import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import VoiceCallComponent from './components/VoiceCallComponent';
import TextChatComponent from './components/TextChatComponent';

const App: React.FC = () => {
    return (
        <Router>
            <Switch>
                <Route path="/voice/:roomId" component={VoiceCallComponent} />
                <Route path="/text-chat" component={TextChatComponent} />
                {/* 추가 라우트가 필요하면 여기에 작성 */}
            </Switch>
        </Router>
    );
};

export default App;
