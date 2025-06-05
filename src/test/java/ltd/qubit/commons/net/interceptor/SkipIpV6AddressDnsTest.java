package ltd.qubit.commons.net.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.jupiter.api.Test;

import okhttp3.Dns;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkipIpV6AddressDnsTest {

    @Test
    void testLookupReturnsOnlyIpv4Addresses() throws UnknownHostException {
        final Dns dns = SkipIpV6AddressDns.INSTANCE;
        // 127.0.0.1 通常只会返回 IPv4 地址
        final List<InetAddress> addresses = dns.lookup("openrouter.ai");
        assertFalse(addresses.isEmpty(), "地址列表不应为空");
        for (final InetAddress addr : addresses) {
            assertTrue(addr instanceof java.net.Inet4Address, "只应返回IPv4地址: " + addr);
        }
    }

    @Test
    void testLookupUnknownHost() {
        final Dns dns = SkipIpV6AddressDns.INSTANCE;
        assertThrows(UnknownHostException.class, () -> dns.lookup("nonexistent.domain.invalid"));
    }
}