package net.sakrak.demoshoppingcart.services

import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class RandomServiceImpl : RandomService {
    private val random = ThreadLocal.withInitial { SecureRandom() }
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ('α'..'ω') + ('א'..'ת')

    override fun nextString(length: Int): String {
        val buf = CharArray(length)

        for (idx in buf.indices) {
            buf[idx] = charPool[random.get().nextInt(charPool.size)]
        }

        return String(buf)
    }
}