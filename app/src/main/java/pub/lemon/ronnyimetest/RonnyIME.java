package pub.lemon.ronnyimetest;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

/**
 * Created by ronny on 17-9-25.
 */

public class RonnyIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener{

    final static  String TAG = "RonnyIME";
    private KeyboardView mKeyboardView;  // 对应skb_container.xml中定义的KeyboardView
    private Keyboard mKeyboard;  // 对应qwerty.xml中定义的Keyboard

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public View onCreateInputView() {
        Log.e(TAG,"Enter onCreateInputView");
        // keyboard被创建后，将调用onCreateInputView函数
        mKeyboardView = (KeyboardView)getLayoutInflater().inflate(R.layout.skb_container,null);
        mKeyboard = new Keyboard(this,R.xml.qwerty);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(this);

        return mKeyboardView;
    }

    @Override
    public void swipeUp() {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    //在该方法中响应按键消息
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.e(TAG,"Enter onKey ---- primaryCode:"+primaryCode+"; keyCodes:"+keyCodes+"; length:"+keyCodes.length);
        for (int i=0;i<keyCodes.length;i++){
            Log.e(TAG,"on onKey --i = "+i+"; -- KEYCODE:"+keyCodes[i]);
        }
        playClick(primaryCode);
        InputConnection ic = getCurrentInputConnection();
        Log.e(TAG,"on onKey -1--- KEYCODE_DELETE:"+Keyboard.KEYCODE_DELETE+"; KEYCODE_DONE:"+Keyboard.KEYCODE_DONE+"; code:"+(char)primaryCode);
        switch (primaryCode){
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_CANCEL:
                handleClose();
                break;
            default:
                char code = (char)primaryCode;
                Log.e(TAG,"on onKey -2--- primaryCode:"+primaryCode+"; code:"+code);
                ic.commitText(String.valueOf(code),1);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }
    private void handleClose() {
        requestHideSelf(0);
        mKeyboardView.closing();
    }


    public void playClick(int keyCode){
        // 点击按键时播放声音，在onKey函数中被调用
        if (keyCode > 0){
            AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
            am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }


}
