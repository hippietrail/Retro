/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retro;

import java.io.IOException;
import java.util.Arrays;

import ghidra.app.util.bin.ByteProvider;
import ghidra.app.util.bin.MemoryByteProvider;
import ghidra.program.model.listing.Program;

public class BinxHexxUtil {
	
	public final static boolean isBinHex( Program program ) {
            ByteProvider provider =
                    MemoryByteProvider.createDefaultAddressSpaceByteProvider(program, true);
            return isBinHex( provider );
    }

    public final static boolean isBinHex( ByteProvider provider ) {
            try {
                    byte[] bytes = provider.readBytes( 0, BinxHexxConstants.MAGIC_BYTES.length );
                    return Arrays.equals( bytes, BinxHexxConstants.MAGIC_BYTES );
            }
            catch (IOException e) {
            }
            return false;
    }

    public final static boolean isBinHex( byte [ ] bytes ) {
            if ( bytes.length >= BinxHexxConstants.MAGIC_BYTES.length ) {
                    return Arrays.equals( bytes, BinxHexxConstants.MAGIC_BYTES );
            }
            return false;
    }
}