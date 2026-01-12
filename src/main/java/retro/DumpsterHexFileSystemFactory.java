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
import ghidra.app.util.bin.ByteProvider;
import ghidra.formats.gfilesystem.*;
import ghidra.formats.gfilesystem.factory.GFileSystemFactoryByteProvider;
import ghidra.formats.gfilesystem.factory.GFileSystemProbeBytesOnly;
import ghidra.util.exception.CancelledException;
import ghidra.util.task.TaskMonitor;

public class DumpsterHexFileSystemFactory implements GFileSystemFactoryByteProvider<DumpsterHexFileSystem>, GFileSystemProbeBytesOnly {

    private static final String EXPECTED_START_BYTES_STRING = "(This file must be converted with BinHex 4.0)";
    private static final byte[] EXPECTED_START_BYTES = EXPECTED_START_BYTES_STRING.getBytes();

    @Override
    public DumpsterHexFileSystem create(FSRLRoot targetFSRL, ByteProvider byteProvider,
            FileSystemService fsService, TaskMonitor monitor)
            throws IOException, CancelledException {
        long length = byteProvider.length(); // or set to appropriate value
        String payloadMD5 = ""; // Calculate or provide the MD5 hash as needed
        return new DumpsterHexFileSystem(targetFSRL, byteProvider, fsService, monitor, length, payloadMD5);
    }

    @Override
    public int getBytesRequired() {
        return EXPECTED_START_BYTES.length; // Use the length of the static constant
    }

    @Override
    public boolean probeStartBytes(FSRL containerFSRL, byte[] startBytes) {
        if (startBytes.length < EXPECTED_START_BYTES.length) {
            return false;
        }
        for (int i = 0; i < EXPECTED_START_BYTES.length; i++) {
            if (startBytes[i] != EXPECTED_START_BYTES[i]) {
                return false;
            }
        }
        return true;
    }
}