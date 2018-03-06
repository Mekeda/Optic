package com.example.abhipubali.cameraappintent.utility;

/**
 * Created by abhipubali on 3/5/18.
 *
 */

import com.microsoft.speech.tts.*;
public class VoiceOutput {
    private Synthesizer m_syn;
    private static String speechApiKey = "22469463e8894b0ca8cf1d5c59d6f24f";
    private Voice v;

    public VoiceOutput()
    {
        m_syn = new Synthesizer(speechApiKey);
        v = new Voice("en-US", "Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)", Voice.Gender.Female, true);
        m_syn.SetVoice(v, null);
    }

    public void generateVoice(String voice)
    {
        m_syn.SpeakToAudio(voice);
    }

}
