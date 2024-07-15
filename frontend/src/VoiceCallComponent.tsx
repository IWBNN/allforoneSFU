import React, { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import KurentoUtils from 'kurento-utils';

const VoiceCallComponent: React.FC = () => {
    const { roomId } = useParams<{ roomId: string }>();
    const localVideoRef = useRef<HTMLVideoElement>(null);
    const remoteVideoRef = useRef<HTMLVideoElement>(null);
    const [isVideoOn, setIsVideoOn] = useState(true);
    const [isAudioOn, setIsAudioOn] = useState(true);
    let webRtcPeer: any;

    useEffect(() => {
        const socket = new WebSocket(`wss://suportscore.site/voice`);

        socket.onopen = () => {
            console.log('WebSocket connection opened to Room: #' + roomId);
            socket.send(JSON.stringify({ id: 'joinRoom', room: roomId }));
        };

        socket.onmessage = (message) => {
            const parsedMessage = JSON.parse(message.data);
            switch (parsedMessage.id) {
                case 'participantJoined':
                    handleParticipantJoined(parsedMessage);
                    break;
                case 'receiveVideoAnswer':
                    webRtcPeer.processAnswer(parsedMessage.sdpAnswer);
                    break;
                case 'iceCandidate':
                    webRtcPeer.addIceCandidate(parsedMessage.candidate);
                    break;
                default:
                    console.error('Unknown message type:', parsedMessage.id);
            }
        };

        const handleParticipantJoined = (message: any) => {
            webRtcPeer = KurentoUtils.WebRtcPeer.WebRtcPeerSendrecv({
                localVideo: localVideoRef.current,
                remoteVideo: remoteVideoRef.current,
                onicecandidate: onIceCandidate
            }, (error: any) => {
                if (error) return console.error(error);
                webRtcPeer.generateOffer(onOffer);
            });

            const onIceCandidate = (candidate: any) => {
                console.log('Local candidate' + JSON.stringify(candidate));
                const message = {
                    id: 'onIceCandidate',
                    candidate: candidate
                };
                socket.send(JSON.stringify(message));
            };

            const onOffer = (error: any, offerSdp: any) => {
                if (error) return console.error(error);
                const message = {
                    id: 'receiveVideoFrom',
                    sdpOffer: offerSdp,
                    sender: message.name
                };
                socket.send(JSON.stringify(message));
            };
        };

        return () => {
            if (webRtcPeer) webRtcPeer.dispose();
            if (socket) socket.close();
        };
    }, [roomId]);

    const toggleVideo = () => {
        if (localVideoRef.current && webRtcPeer) {
            webRtcPeer.getLocalStream().getVideoTracks().forEach((track: any) => track.enabled = !track.enabled);
            setIsVideoOn(prev => !prev);
        }
    };

    const toggleAudio = () => {
        if (localVideoRef.current && webRtcPeer) {
            webRtcPeer.getLocalStream().getAudioTracks().forEach((track: any) => track.enabled = !track.enabled);
            setIsAudioOn(prev => !prev);
        }
    };

    return (
        <div>
            <h1>Voice Call Room #{roomId}</h1>
            <div>
                <video ref={localVideoRef} autoPlay playsInline></video>
                <video ref={remoteVideoRef} autoPlay playsInline></video>
            </div>
            <div>
                <button onClick={toggleVideo}>{isVideoOn ? 'Turn Video Off' : 'Turn Video On'}</button>
                <button onClick={toggleAudio}>{isAudioOn ? 'Turn Audio Off' : 'Turn Audio On'}</button>
            </div>
        </div>
    );
};

export default VoiceCallComponent;
