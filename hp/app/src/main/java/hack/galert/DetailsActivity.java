package hack.galert;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class DetailsActivity extends AppCompatActivity {

    TextView refrence;
    TextView readTime;
    TextView articleHeader;
    TextView articleAbstract;
    TextView likesIcon;
    TextView likes;
    TextView backBtn;
    ImageView articleImage;
    private ArticlesModel data;
    private Serializable receivedBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    public void loadData() {
        receivedBundle = getIntent().getSerializableExtra(Constants.EXTRAA_DETAILS);
        data = (ArticlesModel) receivedBundle;

        Typeface materialTypeFace = FontManager.getInstance(this).getTypeFace(FontManager.FONT_MATERIAL);
        Typeface robotoMedium = FontManager.getInstance(this).getTypeFace(FontManager.FONT_ROBOTO_MEDIUM);
        Typeface robotoRegular = FontManager.getInstance(this).getTypeFace(FontManager.FONT_ROBOTO_REGULAR);


        refrence = (TextView) findViewById(R.id.articleRefrenceDetailsActivity);
        readTime = (TextView) findViewById(R.id.readLengthDetailsActivity);
        articleHeader = (TextView) findViewById(R.id.articlesHeaderDetailsPage);
        articleAbstract = (TextView) findViewById(R.id.articleText);
        likesIcon = (TextView) findViewById(R.id.likesIconHeader);
        likes = (TextView) findViewById(R.id.likesCountHeader);
        articleImage = (ImageView) findViewById(R.id.articleImageDetailsPage);
        backBtn = (TextView) findViewById(R.id.backBtnText);

        refrence.setText(data.refrence);
        readTime.setText(data.readTime);
        articleHeader.setText(data.articleHeader);
        articleAbstract.setText(data.articleAbstract);
        likes.setText(data.likes);

        if (data.isLiked) {
            likesIcon.setTextColor(getResources().getColor(R.color.PrimaryColor));
        }

        if (SharedPreferenceManager.getInstance(this).getChoiceOnImageLoad()) {
            Picasso.with(this)
                    .load(data.imageUrl)
                    .into(articleImage);
        }


    }

    public void attachListeners() {

        likesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.isLiked) {
                    //like();
                }
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getParentActivityIntent());
                finish();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
