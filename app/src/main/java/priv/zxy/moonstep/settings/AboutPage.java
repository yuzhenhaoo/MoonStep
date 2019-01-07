package priv.zxy.moonstep.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import priv.zxy.moonstep.R;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/7
 * 描述:
 **/
public class AboutPage extends AppCompatActivity {

    private TextView content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        initView();
        initData();
    }

    private void initData() {
        StringBuilder textContent = new StringBuilder();
        textContent.append("尊敬的各位用户，你们好。\n" +
                "很荣幸可以在这里说一些关于圆月行的事情，这款app设计出来有多方面的原因，一方面我想要用这款app实现我的第一个大型项目，" +
                "作为基本独立开发的一款app，过程不得不说真的好难，遇到过很多问题，甚至现在其中的一部分我依然没有办法解决，过程中我在努力的学习，" +
                "学以致用的感觉很好，但是我不得不承认我还是太弱了，不能把这款应用做到我理想中的程度，" +
                "但我想我不会停下我的脚步，圆月行也是，会一直有所维护，也相信界面会变得更加友善，更加美观，操作更加流畅。\n" +
                "然后我想说说第二个方面的原因，我一直都觉得社交的圈子好小，不能认识到想要认识的人，不能认识到性格相符合的人，不能认识到真正有缘分的人，我感觉十分" +
                "可惜，我相信大家也是，这也是我的想法来源，我想让这个社会上太多的宅男宅女，多走出房门，可以微微放下手机，跟着地图探索未知，然后可以说，哇！" +
                "原来我生活的地方还有这么令人惊叹的地方啊！\n" +
                "然后我希望你可以把自己家乡美丽的地方分享到社区中，让大家一同感受你感受到的东西。\n" +
                "甚至因此我想到很多丰富这种核心玩法的元素来让这款app的乐趣性更加充足一些。\n" +
                "但是如果你有什么不满意的地方完全可以反馈给我，因为这款产品没有一个真正意义上的产品设计师，只是我顺着自己的想法产生的一个app。\n" +
                "我会聆听每一个人的意见，合理的又或者是不合理的，过程中你会在各种事件中遇到不同的彩蛋，这也是设计中的乐趣之一，找寻并发现这些菜单，用你在探索过程中" +
                "发现的道具去帮助你和他人进行交友，然后努力提升自己的等阶吧！所有的所有都有着它存在的意义，相信我，你会从中得到你想要的东西！\n" +
                "我会继续努力，希望伴随着我的努力，你的缘分也是！");
        content.setText(textContent.toString());
    }

    private void initView() {
        content = findViewById(R.id.content);
    }
}
