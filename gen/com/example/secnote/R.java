/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package com.example.secnote;

public final class R {
    public static final class attr {
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int metaButtonBarButtonStyle=0x7f010001;
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int metaButtonBarStyle=0x7f010000;
    }
    public static final class color {
        public static final int black_overlay=0x7f040000;
    }
    public static final class dimen {
        /**  Default screen margins, per the Android Design guidelines. 

         Example customization of dimensions originally defined in res/values/dimens.xml
         (such as screen margins) for screens with more than 820dp of available width. This
         would include 7" and 10" devices in landscape (~960dp and ~1280dp respectively).
    
         */
        public static final int activity_horizontal_margin=0x7f050000;
        public static final int activity_vertical_margin=0x7f050001;
    }
    public static final class drawable {
        public static final int ic_icon=0x7f020000;
        public static final int ic_icon2=0x7f020001;
        public static final int ic_launcher=0x7f020002;
        public static final int ic_lock_lock=0x7f020003;
        public static final int ic_menu_save=0x7f020004;
        public static final int ic_menu_settings_holo_light=0x7f020005;
        public static final int image_default=0x7f020006;
        public static final int image_default2=0x7f020007;
        public static final int image_touch=0x7f020008;
        public static final int lockyellow=0x7f020009;
        public static final int overlay=0x7f02000a;
        public static final int saveyellow=0x7f02000b;
        public static final int settingyellow=0x7f02000c;
    }
    public static final class id {
        public static final int actionbar_lock=0x7f090006;
        public static final int actionbar_save=0x7f090007;
        public static final int checkpoint_reset=0x7f090009;
        public static final int checkpoint_setimage=0x7f09000a;
        public static final int choose=0x7f090004;
        public static final int editText=0x7f090001;
        public static final int overlay_acvitity=0x7f090002;
        public static final int settings=0x7f090008;
        public static final int take_photo=0x7f090003;
        public static final int toplevel_action=0x7f090005;
        public static final int touch_image=0x7f090000;
    }
    public static final class layout {
        public static final int activity_image=0x7f030000;
        public static final int activity_main=0x7f030001;
        public static final int overlay=0x7f030002;
        public static final int replaceimage=0x7f030003;
    }
    public static final class menu {
        public static final int image=0x7f080000;
        public static final int main=0x7f080001;
    }
    public static final class string {
        public static final int accept=0x7f06000d;
        public static final int action_settings=0x7f060002;
        public static final int actionbar_setting=0x7f060015;
        public static final int app_name=0x7f060000;
        public static final int btn_help=0x7f06000e;
        public static final int btn_load=0x7f060005;
        public static final int btn_lock=0x7f06000f;
        public static final int btn_save=0x7f060004;
        public static final int choose=0x7f060013;
        public static final int dummy_button=0x7f06000b;
        public static final int dummy_content=0x7f06000c;
        public static final int edit_text_hint=0x7f060003;
        public static final int hello_world=0x7f060001;
        public static final int replace_dig=0x7f060014;
        public static final int reset_checkpoints=0x7f060010;
        public static final int reset_image=0x7f060011;
        public static final int take_photo=0x7f060012;
        public static final int title_activity_fullscreen=0x7f06000a;
        public static final int title_activity_image=0x7f060008;
        public static final int toasts_load_error=0x7f060007;
        public static final int toasts_save_error=0x7f060006;
        public static final int touch_checkpoint=0x7f060009;
    }
    public static final class style {
        /** 
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.
    

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.
        

        Base application theme for API 11+. This theme completely replaces
        AppBaseTheme from res/values/styles.xml on API 11+ devices.
    
 API 11 theme customizations can go here. 

        Base application theme for API 14+. This theme completely replaces
        AppBaseTheme from BOTH res/values/styles.xml and
        res/values-v11/styles.xml on API 14+ devices.
    
 API 14 theme customizations can go here. 
         */
        public static final int AppBaseTheme=0x7f070000;
        /**  Application theme. 
 All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f070001;
        public static final int ButtonBar=0x7f070003;
        public static final int ButtonBarButton=0x7f070004;
        public static final int FullscreenActionBarStyle=0x7f070005;
        public static final int FullscreenTheme=0x7f070002;
    }
    public static final class styleable {
        /** 
         Declare custom theme attributes that allow changing which styles are
         used for button bars depending on the API level.
         ?android:attr/buttonBarStyle is new as of API 11 so this is
         necessary to support previous API levels.
    
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_metaButtonBarButtonStyle com.example.secnote:metaButtonBarButtonStyle}</code></td><td></td></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_metaButtonBarStyle com.example.secnote:metaButtonBarStyle}</code></td><td></td></tr>
           </table>
           @see #ButtonBarContainerTheme_metaButtonBarButtonStyle
           @see #ButtonBarContainerTheme_metaButtonBarStyle
         */
        public static final int[] ButtonBarContainerTheme = {
            0x7f010000, 0x7f010001
        };
        /**
          <p>This symbol is the offset where the {@link com.example.secnote.R.attr#metaButtonBarButtonStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name com.example.secnote:metaButtonBarButtonStyle
        */
        public static final int ButtonBarContainerTheme_metaButtonBarButtonStyle = 1;
        /**
          <p>This symbol is the offset where the {@link com.example.secnote.R.attr#metaButtonBarStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name com.example.secnote:metaButtonBarStyle
        */
        public static final int ButtonBarContainerTheme_metaButtonBarStyle = 0;
    };
}
