package Game;
import DataHandling.ConfigHandler;
import DataHandling.ConfigHandler.*;

public class GameSettings {
    private int fieldWidth;
    private int fieldHeight;
    private int gameLevel;
    private boolean musicEnabled;
    private boolean soundEffectsEnabled;
    private boolean extendedMode;


    public int getFieldHeight() {
        this.fieldHeight = ConfigHandler.getFieldHeight();
        System.out.println(this.fieldHeight);
        return this.fieldHeight;
    }
    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight; }


    public int getFieldWidth() {
         this.fieldWidth = ConfigHandler.getFieldWidth();
        System.out.println(this.fieldWidth);

        return this.fieldWidth;
    }
    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public int getGameLevel() {
        return gameLevel;
    }
    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }
    public boolean isMusicEnabled() {
        return musicEnabled;
    }
    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
    }
    public boolean isSoundEffectsEnabled() {
        return soundEffectsEnabled;
    }
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        this.soundEffectsEnabled = soundEffectsEnabled;
    }
    public boolean isExtendedMode() {
        return extendedMode;
    }
    public void setExtendedMode(boolean extendedMode) {
        this.extendedMode = extendedMode;
    }

}


