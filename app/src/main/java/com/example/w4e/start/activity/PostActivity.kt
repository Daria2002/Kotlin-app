package com.example.w4e.start.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.R.id
import com.example.w4e.start.adapter.PostAdapter
import com.example.w4e.start.helper.PostDatabaseHelper
import com.example.w4e.start.helper.UserDatabaseHelper
import com.example.w4e.start.model.Category
import com.example.w4e.start.model.Post


/**
 * This activity is used for displaying posts of selected category
 */
class PostActivity: AppCompatActivity(), View.OnClickListener {
    private val activity = this@PostActivity
    private lateinit var categoryName: AppCompatTextView
    private lateinit var addPostButton: AppCompatButton
    private lateinit var myProfileButton: AppCompatButton
    private lateinit var recyclerViewPosts: RecyclerView
    private lateinit var postsRecyclerAdapter: PostAdapter
    private lateinit var listPosts: MutableList<Post>
    private lateinit var category: String
    private lateinit var user_name: String
    private var postDatabaseHelper: PostDatabaseHelper = PostDatabaseHelper.getInstance(activity)
    private lateinit var userDatabaseHelper: UserDatabaseHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_posts)
        supportActionBar!!.hide()
        category = intent.getStringExtra("CATEGORY").toString()
        user_name = intent.getStringExtra("USER_NAME").toString()
        initViews()
        initListeners()
        initObjects()
    }

    private fun updatePostsInGui() {
        postsRecyclerAdapter = PostAdapter(listPosts, this)
        recyclerViewPosts.adapter = postsRecyclerAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initObjects() {
        userDatabaseHelper = UserDatabaseHelper(activity)
        categoryName.text = category.toUpperCase()
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewPosts.layoutManager = mLayoutManager
        recyclerViewPosts.itemAnimator = DefaultItemAnimator()
        recyclerViewPosts.setHasFixedSize(true)
        listPosts = mutableListOf<Post>()
        listPosts = postDatabaseHelper.getPostsForCategory(category) as MutableList<Post>
        updatePostsInGui()
    }

    private fun initListeners() {
        this.addPostButton.setOnClickListener(this)
        this.myProfileButton.setOnClickListener(this)
    }

    private fun initViews() {
        categoryName = findViewById<AppCompatTextView>(id.textCategoryName)
        recyclerViewPosts = findViewById<RecyclerView>(id.recyclerViewPosts)
        addPostButton = findViewById<AppCompatButton>(id.addPost)
        addPostButton.transformationMethod = null
        myProfileButton = findViewById<AppCompatButton>(R.id.myProfile)
        myProfileButton.transformationMethod = null
    }

    /**
     * Open post for more info.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View) {
        when(v.id) {
            R.id.addPost -> addNewPost(activity)
            R.id.postText -> showPostDetails(activity, v.tag as Post)
            R.id.myProfile -> openProfile(user_name)
        }
    }

    private fun showPostDetails(c: Context, post: Post) {
        val postEditText = EditText(c)
        var postDetails = post.text + "\nCreated by: " + postDatabaseHelper.getPostCreator(post) +
                "\nPosted on: " + post.time
        postEditText.setTextColor(Color.WHITE)
        val dialog = AlertDialog.Builder(c, R.style.Work4Experience_AddPostDialog)
            .setTitle("Post details")
            .setMessage(postDetails)
            .setPositiveButton("Bid"
            ) { _, _ -> sendAnEmail(post.user_name, user_name) }
            .setNegativeButton("Cancel", null)
            .setNeutralButton("User profile") { _, _ -> openProfile(post.user_name) }
            .create()
        dialog.show()
    }

    private fun openProfile(userName: String) {
        val intent = Intent(applicationContext, UserProfileActivity::class.java)
        intent.putExtra("USER_NAME", userName)
        intent.putExtra("CV_URL", userDatabaseHelper.getCV(userName))
        startActivity(intent)
    }

    private fun sendAnEmail(receiverName: String, senderName: String) {
        var receiverEmail = userDatabaseHelper.getUserEmail(receiverName)
        var senderEmail = userDatabaseHelper.getUserEmail(senderName)
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(receiverEmail))
        //put the Subject in the intent
        var subject = "[Work4Experience] Post bid"
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        var message = "Hello $receiverName,\n\nI have bid your post.\n\nBest regards,\n$senderName"
        mIntent.putExtra(Intent.EXTRA_TEXT, message)
        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addNewPost(c: Context) {
        val postEditText = EditText(c)
        postEditText.setTextColor(Color.WHITE)
        val dialog = AlertDialog.Builder(c, R.style.Work4Experience_AddPostDialog)
            .setTitle("Add a new post")
            .setMessage("What do you want to post?")
            .setView(postEditText)
            .setPositiveButton("Add"
            ) { _, _ -> updatePostsInDb(postEditText.text.toString()) }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePostsInDb(text: String) {
        var newPost = Post(text = text,
            user_name = user_name,
            category = PostDatabaseHelper.titleToCategory(
                category))
        postDatabaseHelper.addPost(newPost)
        listPosts.add(newPost)
        updatePostsInGui()
    }
}