package ac.su.allforonesfu.service;

import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;
import org.springframework.stereotype.Service;

@Service
public class KurentoService {

    private KurentoClient kurentoClient;
    private MediaPipeline mediaPipeline;

    public KurentoService() {
        this.kurentoClient = KurentoClient.create("ws://localhost:8888/kurento");
        this.mediaPipeline = kurentoClient.createMediaPipeline();
    }

    public WebRtcEndpoint createWebRtcEndpoint() {
        return new WebRtcEndpoint.Builder(mediaPipeline).build
