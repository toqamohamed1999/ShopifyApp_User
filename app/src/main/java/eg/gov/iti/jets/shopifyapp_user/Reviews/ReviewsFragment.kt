package eg.gov.iti.jets.shopifyapp_user.Reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.Review
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentProductDetailsBinding
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentReviewsBinding
import eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.ui.ProductDetailsFragmentArgs


class ReviewsFragment : Fragment() {
    lateinit var binding: FragmentReviewsBinding
    val args: ReviewsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.GONE
        val reviews = listOf(
            Review(
                "John Smith",
                4.5,
                "https://images.pexels.com/photos/614810/pexels-photo-614810.jpeg?cs=srgb&dl=pexels-simon-robben-614810.jpg&fm=jpg",
                "I really enjoyed using this product. It exceeded my expectations and I would definitely recommend it to anyone looking for a reliable solution."
            ),
            Review(
                "Jane Doe",
                3.0,
                "https://t4.ftcdn.net/jpg/03/83/25/83/360_F_383258331_D8imaEMl8Q3lf7EKU2Pi78Cn0R7KkW9o.jpg",
                "The product was okay, but it didn't quite meet my needs. I found it a bit difficult to use and would have liked more guidance."
            ),
            Review(
                "David Lee",
                5.0,
                "https://d34u8crftukxnk.cloudfront.net/slackpress/prod/sites/6/E12KS1G65-W0168RE00G7-133faf432639-512.jpeg",
                "This product is fantastic! It's incredibly easy to use and has saved me a lot of time and effort. I highly recommend it."
            ),
            Review(
                "Sarah Johnson",
                2.5,
                "https://img.freepik.com/free-photo/portrait-happy-young-woman-looking-camera_23-2147892777.jpg?w=2000",
                "I was disappointed with this product. It didn't work as I had hoped and I had trouble getting support from the company."
            ),
            
            Review(
                "Emily Nguyen",
                3.5,
                "https://paramotoroutlet.com/jpg/1594912196352.jpg",
                "I like this product, but I think it could use some improvements. There are a few bugs that need to be fixed and some features that could be added."
            ),
            Review(
                "James Kim",
                4.5,
                "https://sb.kaleidousercontent.com/67418/1920x1545/c5f15ac173/samuel-raita-ridxdghg7pw-unsplash.jpg",
                "I am very impressed with this product. It has exceeded my expectations and I would definitely recommend it to others."
            ),
            Review(
                "Lisa Chen",
                2.0,
                "https://images.pexels.com/photos/2726111/pexels-photo-2726111.jpeg?cs=srgb&dl=pexels-masha-raymers-2726111.jpg&fm=jpg",
                "Unfortunately, this product did not work for me. I had trouble getting it to function properly and ultimately had to return it."
            ),
            Review(
                "Andrew Garcia",
                4.0,
                "https://www.morganstanley.com/content/dam/msdotcom/people/tiles/isaiah-dwuma.jpg.img.490.medium.jpg/1594668408164.jpg",
                "I think this product is great. It has all the features I need and the user interface is very intuitive. The only downside is that it's a bit expensive."
            ),
            Review(
                "Maria Hernandez",
                3.0,
                "https://www.shutterstock.com/image-photo/photo-cheerful-minded-lady-interested-260nw-2047307549.jpg",
                "This product is okay, but it's not as good as some of the other options out there. I found it a bit clunky to use and it didn't have all the features I needed."
            ),
            Review(
                "Kevin Patel",
                5.0,
                "https://sb.kaleidousercontent.com/67418/1672x1018/6463a5af0d/screenshot-2022-05-24-at-15-22-28.png",
                "I love this product! It's incredibly easy to use and has saved me a ton of time. I would highly recommend it to anyone."
            ),

            Review(
                "William Lee",
                4.5,
                "https://qph.cf2.quoracdn.net/main-thumb-1952403031-200-xwoiecpkbigonvtjrbtfprmtrplgldrf.jpeg",
                "This product is amazing! It's very user-friendly and has all the features I need. I would definitely recommend it to anyone looking for a great solution."
            ),
            Review(
                "Ashley Davis",
                2.5,
                "https://images.pexels.com/photos/3763188/pexels-photo-3763188.jpeg?cs=srgb&dl=pexels-andrea-piacquadio-3763188.jpg&fm=jpg",
                "I was disappointed with this product. It didn't work as well as I had hoped and I had trouble getting support from the company."
            ),
            Review(
                "Michaela Brown",
                4.0,
                "https://media.glamour.com/photos/5695aa8e93ef4b09520dfd8f/master/w_320%2Cc_limit/sex-love-life-2009-12-1207-01_profile_pic_li.jpg",
                "I think this product is great. It's very easy to use and has all the features I need. The only downside is that it's a bit pricey."
            ),
            Review(
                "David Johnson",
                3.5,
                "https://qph.cf2.quoracdn.net/main-thumb-1885335642-200-carvuocvxkskxyhdtyolspryllpahcoe.jpeg",
                "I like this product, but it has some issues. There are a few bugs that need to be fixed and some features that could be added to make it even better."
            ),
            Review(
                "Jessica Chen",
                4.5,
                "https://www.morganstanley.com/content/dam/msdotcom/people/tiles/miriam-faghihi.jpg.img.490.medium.jpg/1595876429967.jpg",
                "I am very happy with this product. It has all the features I need and the customer service is excellent. I would definitely recommend it."
            ),
            Review(
                "Brian Kim",
                2.0,
                "https://qph.cf2.quoracdn.net/main-thumb-1745939686-200-kydmzzqpnznxamtydfuhnxjkelhxkvip.jpeg",
                "I had lot of trouble with this product. It was difficult to use and I had trouble getting it to work properly."
            ),
            Review(
                "Erica Nguyen",
                4.0,
                "https://www.morganstanley.com/content/dam/msdotcom/people/tiles/Tomoko-tile-original-877x877.jpg.img.490.medium.jpg/1631896424085.jpg",
                "Overall, I think this product is very good. It has all the features I need and the user interface is very intuitive."
            ),
            Review(
                "Daniel Lee",
                3.5,
                "https://sb.kaleidousercontent.com/67418/1920x1281/0e9f02a048/christian-buehner-ditylc26zvi-unsplash.jpg",
                "I think this product is decent, but it could use some improvements. There are a few areas where it falls short compared to other options on the market."
            )
        )

        val productName = args.productName
        val productImage = args.productImage
        binding.txtProductName.text = productName
        Glide.with(binding.root.context)
            .load(productImage)
            .error(R.drawable.noimage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.productImage)
        binding.reviewsRecycler.adapter=ReviewsAdapter(requireContext(),reviews)

    }
}