import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityRegister3Binding
import com.example.myapplication.loginScreen.LoginPage

class Register3 : AppCompatActivity() {

    private lateinit var binding: ActivityRegister3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val singletonName = Singelton.name
        if (singletonName != null) {
            binding.nameSingelton.text = singletonName
        } else {
            // Singleton'daki 'name' özelliği null ise, ekrandaki öğeye bir varsayılan değer atayabilirsiniz veya atama işlemini atlayabilirsiniz.
            // Örnek: binding.nameSingelton.text = "Varsayılan Değer"
        }

        // EditText öğesini düzenlenebilir hale getirin
        binding.nameSingelton.isEnabled = true
    }

    fun goSuccessPage(view: View) {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }
}
