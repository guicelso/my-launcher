package kotlin.reflect.jvm.internal.impl.metadata.jvm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractParser;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringArrayList;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringList;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.protobuf.ProtocolStringList;

/* JADX INFO: loaded from: classes4.dex */
public final class JvmModuleProtoBuf {

    public interface ModuleOrBuilder extends MessageLiteOrBuilder {
    }

    public interface PackagePartsOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class Module extends GeneratedMessageLite implements ModuleOrBuilder {
        public static Parser<Module> PARSER = new AbstractParser<Module>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmModuleProtoBuf.Module.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Module parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Module(input, extensionRegistry);
            }
        };
        private static final Module defaultInstance = new Module(true);
        private List<ProtoBuf.Annotation> annotation_;
        private int bitField0_;
        private LazyStringList jvmPackageName_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<PackageParts> metadataParts_;
        private List<PackageParts> packageParts_;
        private ProtoBuf.QualifiedNameTable qualifiedNameTable_;
        private ProtoBuf.StringTable stringTable_;
        private final ByteString unknownFields;

        private Module(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Module(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static Module getDefaultInstance() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Module getDefaultInstanceForType() {
            return defaultInstance;
        }

        private Module(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int i = 0;
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag == 0) {
                            z = true;
                        } else if (tag == 10) {
                            if ((i & 1) != 1) {
                                this.packageParts_ = new ArrayList();
                                i |= 1;
                            }
                            this.packageParts_.add((PackageParts) codedInputStream.readMessage(PackageParts.PARSER, extensionRegistryLite));
                        } else if (tag == 18) {
                            if ((i & 2) != 2) {
                                this.metadataParts_ = new ArrayList();
                                i |= 2;
                            }
                            this.metadataParts_.add((PackageParts) codedInputStream.readMessage(PackageParts.PARSER, extensionRegistryLite));
                        } else if (tag == 26) {
                            ByteString bytes = codedInputStream.readBytes();
                            if ((i & 4) != 4) {
                                this.jvmPackageName_ = new LazyStringArrayList();
                                i |= 4;
                            }
                            this.jvmPackageName_.add(bytes);
                        } else if (tag == 34) {
                            ProtoBuf.StringTable.Builder builder = (this.bitField0_ & 1) == 1 ? this.stringTable_.toBuilder() : null;
                            this.stringTable_ = (ProtoBuf.StringTable) codedInputStream.readMessage(ProtoBuf.StringTable.PARSER, extensionRegistryLite);
                            if (builder != null) {
                                builder.mergeFrom(this.stringTable_);
                                this.stringTable_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag == 42) {
                            ProtoBuf.QualifiedNameTable.Builder builder2 = (this.bitField0_ & 2) == 2 ? this.qualifiedNameTable_.toBuilder() : null;
                            this.qualifiedNameTable_ = (ProtoBuf.QualifiedNameTable) codedInputStream.readMessage(ProtoBuf.QualifiedNameTable.PARSER, extensionRegistryLite);
                            if (builder2 != null) {
                                builder2.mergeFrom(this.qualifiedNameTable_);
                                this.qualifiedNameTable_ = builder2.buildPartial();
                            }
                            this.bitField0_ |= 2;
                        } else if (tag == 50) {
                            if ((i & 32) != 32) {
                                this.annotation_ = new ArrayList();
                                i |= 32;
                            }
                            this.annotation_.add((ProtoBuf.Annotation) codedInputStream.readMessage(ProtoBuf.Annotation.PARSER, extensionRegistryLite));
                        } else if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            z = true;
                        }
                    } catch (Throwable th) {
                        if ((i & 1) == 1) {
                            this.packageParts_ = Collections.unmodifiableList(this.packageParts_);
                        }
                        if ((i & 2) == 2) {
                            this.metadataParts_ = Collections.unmodifiableList(this.metadataParts_);
                        }
                        if ((i & 4) == 4) {
                            this.jvmPackageName_ = this.jvmPackageName_.getUnmodifiableView();
                        }
                        if ((i & 32) == 32) {
                            this.annotation_ = Collections.unmodifiableList(this.annotation_);
                        }
                        try {
                            codedOutputStreamNewInstance.flush();
                        } catch (IOException e) {
                        } finally {
                            this.unknownFields = outputNewOutput.toByteString();
                        }
                        makeExtensionsImmutable();
                        throw th;
                    }
                } catch (InvalidProtocolBufferException e2) {
                    throw e2.setUnfinishedMessage(this);
                } catch (IOException e3) {
                    throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                }
            }
            if ((i & 1) == 1) {
                this.packageParts_ = Collections.unmodifiableList(this.packageParts_);
            }
            if ((i & 2) == 2) {
                this.metadataParts_ = Collections.unmodifiableList(this.metadataParts_);
            }
            if ((i & 4) == 4) {
                this.jvmPackageName_ = this.jvmPackageName_.getUnmodifiableView();
            }
            if ((i & 32) == 32) {
                this.annotation_ = Collections.unmodifiableList(this.annotation_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException e4) {
            } finally {
                this.unknownFields = outputNewOutput.toByteString();
            }
            makeExtensionsImmutable();
        }

        static {
            defaultInstance.initFields();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Module> getParserForType() {
            return PARSER;
        }

        public List<PackageParts> getPackagePartsList() {
            return this.packageParts_;
        }

        public int getPackagePartsCount() {
            return this.packageParts_.size();
        }

        public PackageParts getPackageParts(int index) {
            return this.packageParts_.get(index);
        }

        public List<PackageParts> getMetadataPartsList() {
            return this.metadataParts_;
        }

        public int getMetadataPartsCount() {
            return this.metadataParts_.size();
        }

        public PackageParts getMetadataParts(int index) {
            return this.metadataParts_.get(index);
        }

        public ProtocolStringList getJvmPackageNameList() {
            return this.jvmPackageName_;
        }

        public boolean hasStringTable() {
            return (this.bitField0_ & 1) == 1;
        }

        public ProtoBuf.StringTable getStringTable() {
            return this.stringTable_;
        }

        public boolean hasQualifiedNameTable() {
            return (this.bitField0_ & 2) == 2;
        }

        public ProtoBuf.QualifiedNameTable getQualifiedNameTable() {
            return this.qualifiedNameTable_;
        }

        public List<ProtoBuf.Annotation> getAnnotationList() {
            return this.annotation_;
        }

        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        public ProtoBuf.Annotation getAnnotation(int index) {
            return this.annotation_.get(index);
        }

        private void initFields() {
            this.packageParts_ = Collections.emptyList();
            this.metadataParts_ = Collections.emptyList();
            this.jvmPackageName_ = LazyStringArrayList.EMPTY;
            this.stringTable_ = ProtoBuf.StringTable.getDefaultInstance();
            this.qualifiedNameTable_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
            this.annotation_ = Collections.emptyList();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < getPackagePartsCount(); i++) {
                if (!getPackageParts(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i2 = 0; i2 < getMetadataPartsCount(); i2++) {
                if (!getMetadataParts(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasQualifiedNameTable() && !getQualifiedNameTable().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i3 = 0; i3 < getAnnotationCount(); i3++) {
                if (!getAnnotation(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            for (int i = 0; i < this.packageParts_.size(); i++) {
                output.writeMessage(1, this.packageParts_.get(i));
            }
            for (int i2 = 0; i2 < this.metadataParts_.size(); i2++) {
                output.writeMessage(2, this.metadataParts_.get(i2));
            }
            for (int i3 = 0; i3 < this.jvmPackageName_.size(); i3++) {
                output.writeBytes(3, this.jvmPackageName_.getByteString(i3));
            }
            int i4 = this.bitField0_;
            if ((i4 & 1) == 1) {
                output.writeMessage(4, this.stringTable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(5, this.qualifiedNameTable_);
            }
            for (int i5 = 0; i5 < this.annotation_.size(); i5++) {
                output.writeMessage(6, this.annotation_.get(i5));
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            for (int i = 0; i < this.packageParts_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(1, this.packageParts_.get(i));
            }
            for (int i2 = 0; i2 < this.metadataParts_.size(); i2++) {
                size2 += CodedOutputStream.computeMessageSize(2, this.metadataParts_.get(i2));
            }
            int dataSize = 0;
            for (int i3 = 0; i3 < this.jvmPackageName_.size(); i3++) {
                dataSize += CodedOutputStream.computeBytesSizeNoTag(this.jvmPackageName_.getByteString(i3));
            }
            int size3 = size2 + dataSize + (getJvmPackageNameList().size() * 1);
            int dataSize2 = this.bitField0_;
            if ((dataSize2 & 1) == 1) {
                size3 += CodedOutputStream.computeMessageSize(4, this.stringTable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size3 += CodedOutputStream.computeMessageSize(5, this.qualifiedNameTable_);
            }
            for (int i4 = 0; i4 < this.annotation_.size(); i4++) {
                size3 += CodedOutputStream.computeMessageSize(6, this.annotation_.get(i4));
            }
            int size4 = size3 + this.unknownFields.size();
            this.memoizedSerializedSize = size4;
            return size4;
        }

        public static Module parseFrom(InputStream input) throws IOException {
            return PARSER.parseFrom(input);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(Module prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Module, Builder> implements ModuleOrBuilder {
            private int bitField0_;
            private List<PackageParts> packageParts_ = Collections.emptyList();
            private List<PackageParts> metadataParts_ = Collections.emptyList();
            private LazyStringList jvmPackageName_ = LazyStringArrayList.EMPTY;
            private ProtoBuf.StringTable stringTable_ = ProtoBuf.StringTable.getDefaultInstance();
            private ProtoBuf.QualifiedNameTable qualifiedNameTable_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
            private List<ProtoBuf.Annotation> annotation_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* JADX INFO: renamed from: clone */
            public Builder mo379clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Module getDefaultInstanceForType() {
                return Module.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Module build() {
                Module result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public Module buildPartial() {
                Module result = new Module(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.packageParts_ = Collections.unmodifiableList(this.packageParts_);
                    this.bitField0_ &= -2;
                }
                result.packageParts_ = this.packageParts_;
                if ((this.bitField0_ & 2) == 2) {
                    this.metadataParts_ = Collections.unmodifiableList(this.metadataParts_);
                    this.bitField0_ &= -3;
                }
                result.metadataParts_ = this.metadataParts_;
                if ((this.bitField0_ & 4) == 4) {
                    this.jvmPackageName_ = this.jvmPackageName_.getUnmodifiableView();
                    this.bitField0_ &= -5;
                }
                result.jvmPackageName_ = this.jvmPackageName_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ = 0 | 1;
                }
                result.stringTable_ = this.stringTable_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 2;
                }
                result.qualifiedNameTable_ = this.qualifiedNameTable_;
                if ((this.bitField0_ & 32) == 32) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                    this.bitField0_ &= -33;
                }
                result.annotation_ = this.annotation_;
                result.bitField0_ = to_bitField0_;
                return result;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Module other) {
                if (other == Module.getDefaultInstance()) {
                    return this;
                }
                if (!other.packageParts_.isEmpty()) {
                    if (this.packageParts_.isEmpty()) {
                        this.packageParts_ = other.packageParts_;
                        this.bitField0_ &= -2;
                    } else {
                        ensurePackagePartsIsMutable();
                        this.packageParts_.addAll(other.packageParts_);
                    }
                }
                if (!other.metadataParts_.isEmpty()) {
                    if (this.metadataParts_.isEmpty()) {
                        this.metadataParts_ = other.metadataParts_;
                        this.bitField0_ &= -3;
                    } else {
                        ensureMetadataPartsIsMutable();
                        this.metadataParts_.addAll(other.metadataParts_);
                    }
                }
                if (!other.jvmPackageName_.isEmpty()) {
                    if (this.jvmPackageName_.isEmpty()) {
                        this.jvmPackageName_ = other.jvmPackageName_;
                        this.bitField0_ &= -5;
                    } else {
                        ensureJvmPackageNameIsMutable();
                        this.jvmPackageName_.addAll(other.jvmPackageName_);
                    }
                }
                if (other.hasStringTable()) {
                    mergeStringTable(other.getStringTable());
                }
                if (other.hasQualifiedNameTable()) {
                    mergeQualifiedNameTable(other.getQualifiedNameTable());
                }
                if (!other.annotation_.isEmpty()) {
                    if (this.annotation_.isEmpty()) {
                        this.annotation_ = other.annotation_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureAnnotationIsMutable();
                        this.annotation_.addAll(other.annotation_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getPackagePartsCount(); i++) {
                    if (!getPackageParts(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i2 = 0; i2 < getMetadataPartsCount(); i2++) {
                    if (!getMetadataParts(i2).isInitialized()) {
                        return false;
                    }
                }
                if (hasQualifiedNameTable() && !getQualifiedNameTable().isInitialized()) {
                    return false;
                }
                for (int i3 = 0; i3 < getAnnotationCount(); i3++) {
                    if (!getAnnotation(i3).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                try {
                    try {
                        Module parsedMessage = Module.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        throw e;
                    }
                } catch (Throwable th) {
                    if (0 != 0) {
                        mergeFrom((Module) null);
                    }
                    throw th;
                }
            }

            private void ensurePackagePartsIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.packageParts_ = new ArrayList(this.packageParts_);
                    this.bitField0_ |= 1;
                }
            }

            public int getPackagePartsCount() {
                return this.packageParts_.size();
            }

            public PackageParts getPackageParts(int index) {
                return this.packageParts_.get(index);
            }

            private void ensureMetadataPartsIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.metadataParts_ = new ArrayList(this.metadataParts_);
                    this.bitField0_ |= 2;
                }
            }

            public int getMetadataPartsCount() {
                return this.metadataParts_.size();
            }

            public PackageParts getMetadataParts(int index) {
                return this.metadataParts_.get(index);
            }

            private void ensureJvmPackageNameIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.jvmPackageName_ = new LazyStringArrayList(this.jvmPackageName_);
                    this.bitField0_ |= 4;
                }
            }

            public Builder mergeStringTable(ProtoBuf.StringTable value) {
                if ((this.bitField0_ & 8) == 8 && this.stringTable_ != ProtoBuf.StringTable.getDefaultInstance()) {
                    this.stringTable_ = ProtoBuf.StringTable.newBuilder(this.stringTable_).mergeFrom(value).buildPartial();
                } else {
                    this.stringTable_ = value;
                }
                this.bitField0_ |= 8;
                return this;
            }

            public boolean hasQualifiedNameTable() {
                return (this.bitField0_ & 16) == 16;
            }

            public ProtoBuf.QualifiedNameTable getQualifiedNameTable() {
                return this.qualifiedNameTable_;
            }

            public Builder mergeQualifiedNameTable(ProtoBuf.QualifiedNameTable value) {
                if ((this.bitField0_ & 16) == 16 && this.qualifiedNameTable_ != ProtoBuf.QualifiedNameTable.getDefaultInstance()) {
                    this.qualifiedNameTable_ = ProtoBuf.QualifiedNameTable.newBuilder(this.qualifiedNameTable_).mergeFrom(value).buildPartial();
                } else {
                    this.qualifiedNameTable_ = value;
                }
                this.bitField0_ |= 16;
                return this;
            }

            private void ensureAnnotationIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.annotation_ = new ArrayList(this.annotation_);
                    this.bitField0_ |= 32;
                }
            }

            public int getAnnotationCount() {
                return this.annotation_.size();
            }

            public ProtoBuf.Annotation getAnnotation(int index) {
                return this.annotation_.get(index);
            }
        }
    }

    public static final class PackageParts extends GeneratedMessageLite implements PackagePartsOrBuilder {
        public static Parser<PackageParts> PARSER = new AbstractParser<PackageParts>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmModuleProtoBuf.PackageParts.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public PackageParts parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new PackageParts(input, extensionRegistry);
            }
        };
        private static final PackageParts defaultInstance = new PackageParts(true);
        private int bitField0_;
        private int classWithJvmPackageNamePackageIdMemoizedSerializedSize;
        private List<Integer> classWithJvmPackageNamePackageId_;
        private LazyStringList classWithJvmPackageNameShortName_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int multifileFacadeShortNameIdMemoizedSerializedSize;
        private List<Integer> multifileFacadeShortNameId_;
        private LazyStringList multifileFacadeShortName_;
        private Object packageFqName_;
        private LazyStringList shortClassName_;
        private final ByteString unknownFields;

        private PackageParts(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.multifileFacadeShortNameIdMemoizedSerializedSize = -1;
            this.classWithJvmPackageNamePackageIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private PackageParts(boolean noInit) {
            this.multifileFacadeShortNameIdMemoizedSerializedSize = -1;
            this.classWithJvmPackageNamePackageIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static PackageParts getDefaultInstance() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public PackageParts getDefaultInstanceForType() {
            return defaultInstance;
        }

        private PackageParts(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            boolean z;
            this.multifileFacadeShortNameIdMemoizedSerializedSize = -1;
            this.classWithJvmPackageNamePackageIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output unknownFieldsOutput = ByteString.newOutput();
            boolean done = true;
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput, 1);
            boolean done2 = false;
            int mutable_bitField0_ = 0;
            while (!done2) {
                try {
                    int tag = input.readTag();
                    if (tag == 0) {
                        z = done;
                        done2 = true;
                    } else if (tag == 10) {
                        ByteString bs = input.readBytes();
                        z = true;
                        this.bitField0_ |= 1;
                        this.packageFqName_ = bs;
                    } else if (tag == 18) {
                        ByteString bs2 = input.readBytes();
                        if ((mutable_bitField0_ & 2) != 2) {
                            this.shortClassName_ = new LazyStringArrayList();
                            mutable_bitField0_ |= 2;
                        }
                        this.shortClassName_.add(bs2);
                        z = true;
                    } else if (tag == 24) {
                        if ((mutable_bitField0_ & 4) != 4) {
                            this.multifileFacadeShortNameId_ = new ArrayList();
                            mutable_bitField0_ |= 4;
                        }
                        this.multifileFacadeShortNameId_.add(Integer.valueOf(input.readInt32()));
                        z = true;
                    } else if (tag == 26) {
                        int length = input.readRawVarint32();
                        int limit = input.pushLimit(length);
                        if ((mutable_bitField0_ & 4) != 4 && input.getBytesUntilLimit() > 0) {
                            this.multifileFacadeShortNameId_ = new ArrayList();
                            mutable_bitField0_ |= 4;
                        }
                        while (input.getBytesUntilLimit() > 0) {
                            this.multifileFacadeShortNameId_.add(Integer.valueOf(input.readInt32()));
                        }
                        input.popLimit(limit);
                        z = true;
                    } else if (tag == 34) {
                        ByteString bs3 = input.readBytes();
                        if ((mutable_bitField0_ & 8) != 8) {
                            this.multifileFacadeShortName_ = new LazyStringArrayList();
                            mutable_bitField0_ |= 8;
                        }
                        this.multifileFacadeShortName_.add(bs3);
                        z = true;
                    } else if (tag == 42) {
                        ByteString bs4 = input.readBytes();
                        if ((mutable_bitField0_ & 16) != 16) {
                            this.classWithJvmPackageNameShortName_ = new LazyStringArrayList();
                            mutable_bitField0_ |= 16;
                        }
                        this.classWithJvmPackageNameShortName_.add(bs4);
                        z = true;
                    } else if (tag == 48) {
                        if ((mutable_bitField0_ & 32) != 32) {
                            this.classWithJvmPackageNamePackageId_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        this.classWithJvmPackageNamePackageId_.add(Integer.valueOf(input.readInt32()));
                        z = true;
                    } else if (tag != 50) {
                        try {
                            try {
                                if (parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                                    z = done;
                                } else {
                                    done2 = true;
                                    z = done;
                                }
                            } catch (Throwable th) {
                                e = th;
                                InvalidProtocolBufferException invalidProtocolBufferException = e;
                                if ((mutable_bitField0_ & 2) == 2) {
                                    this.shortClassName_ = this.shortClassName_.getUnmodifiableView();
                                }
                                if ((mutable_bitField0_ & 4) == 4) {
                                    this.multifileFacadeShortNameId_ = Collections.unmodifiableList(this.multifileFacadeShortNameId_);
                                }
                                if ((mutable_bitField0_ & 8) == 8) {
                                    this.multifileFacadeShortName_ = this.multifileFacadeShortName_.getUnmodifiableView();
                                }
                                if ((mutable_bitField0_ & 16) == 16) {
                                    this.classWithJvmPackageNameShortName_ = this.classWithJvmPackageNameShortName_.getUnmodifiableView();
                                }
                                if ((mutable_bitField0_ & 32) == 32) {
                                    this.classWithJvmPackageNamePackageId_ = Collections.unmodifiableList(this.classWithJvmPackageNamePackageId_);
                                }
                                try {
                                    unknownFieldsCodedOutput.flush();
                                } catch (IOException e) {
                                } finally {
                                    this.unknownFields = unknownFieldsOutput.toByteString();
                                }
                                makeExtensionsImmutable();
                                throw invalidProtocolBufferException;
                            }
                        } catch (InvalidProtocolBufferException e2) {
                            e = e2;
                            throw e.setUnfinishedMessage(this);
                        } catch (IOException e3) {
                            e = e3;
                            throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
                        }
                    } else {
                        int length2 = input.readRawVarint32();
                        int limit2 = input.pushLimit(length2);
                        if ((mutable_bitField0_ & 32) != 32 && input.getBytesUntilLimit() > 0) {
                            this.classWithJvmPackageNamePackageId_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        while (input.getBytesUntilLimit() > 0) {
                            this.classWithJvmPackageNamePackageId_.add(Integer.valueOf(input.readInt32()));
                        }
                        input.popLimit(limit2);
                        z = true;
                    }
                    done = z;
                } catch (InvalidProtocolBufferException e4) {
                    e = e4;
                } catch (IOException e5) {
                    e = e5;
                } catch (Throwable th2) {
                    e = th2;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.shortClassName_ = this.shortClassName_.getUnmodifiableView();
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.multifileFacadeShortNameId_ = Collections.unmodifiableList(this.multifileFacadeShortNameId_);
            }
            if ((mutable_bitField0_ & 8) == 8) {
                this.multifileFacadeShortName_ = this.multifileFacadeShortName_.getUnmodifiableView();
            }
            if ((mutable_bitField0_ & 16) == 16) {
                this.classWithJvmPackageNameShortName_ = this.classWithJvmPackageNameShortName_.getUnmodifiableView();
            }
            if ((mutable_bitField0_ & 32) == 32) {
                this.classWithJvmPackageNamePackageId_ = Collections.unmodifiableList(this.classWithJvmPackageNamePackageId_);
            }
            try {
                unknownFieldsCodedOutput.flush();
            } catch (IOException e6) {
            } finally {
                this.unknownFields = unknownFieldsOutput.toByteString();
            }
            makeExtensionsImmutable();
        }

        static {
            defaultInstance.initFields();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<PackageParts> getParserForType() {
            return PARSER;
        }

        public boolean hasPackageFqName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getPackageFqName() {
            Object ref = this.packageFqName_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.packageFqName_ = s;
            }
            return s;
        }

        public ByteString getPackageFqNameBytes() {
            Object ref = this.packageFqName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.packageFqName_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        public ProtocolStringList getShortClassNameList() {
            return this.shortClassName_;
        }

        public List<Integer> getMultifileFacadeShortNameIdList() {
            return this.multifileFacadeShortNameId_;
        }

        public ProtocolStringList getMultifileFacadeShortNameList() {
            return this.multifileFacadeShortName_;
        }

        public ProtocolStringList getClassWithJvmPackageNameShortNameList() {
            return this.classWithJvmPackageNameShortName_;
        }

        public List<Integer> getClassWithJvmPackageNamePackageIdList() {
            return this.classWithJvmPackageNamePackageId_;
        }

        private void initFields() {
            this.packageFqName_ = "";
            this.shortClassName_ = LazyStringArrayList.EMPTY;
            this.multifileFacadeShortNameId_ = Collections.emptyList();
            this.multifileFacadeShortName_ = LazyStringArrayList.EMPTY;
            this.classWithJvmPackageNameShortName_ = LazyStringArrayList.EMPTY;
            this.classWithJvmPackageNamePackageId_ = Collections.emptyList();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasPackageFqName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getPackageFqNameBytes());
            }
            for (int i = 0; i < this.shortClassName_.size(); i++) {
                output.writeBytes(2, this.shortClassName_.getByteString(i));
            }
            if (getMultifileFacadeShortNameIdList().size() > 0) {
                output.writeRawVarint32(26);
                output.writeRawVarint32(this.multifileFacadeShortNameIdMemoizedSerializedSize);
            }
            for (int i2 = 0; i2 < this.multifileFacadeShortNameId_.size(); i2++) {
                output.writeInt32NoTag(this.multifileFacadeShortNameId_.get(i2).intValue());
            }
            for (int i3 = 0; i3 < this.multifileFacadeShortName_.size(); i3++) {
                output.writeBytes(4, this.multifileFacadeShortName_.getByteString(i3));
            }
            for (int i4 = 0; i4 < this.classWithJvmPackageNameShortName_.size(); i4++) {
                output.writeBytes(5, this.classWithJvmPackageNameShortName_.getByteString(i4));
            }
            if (getClassWithJvmPackageNamePackageIdList().size() > 0) {
                output.writeRawVarint32(50);
                output.writeRawVarint32(this.classWithJvmPackageNamePackageIdMemoizedSerializedSize);
            }
            for (int i5 = 0; i5 < this.classWithJvmPackageNamePackageId_.size(); i5++) {
                output.writeInt32NoTag(this.classWithJvmPackageNamePackageId_.get(i5).intValue());
            }
            output.writeRawBytes(this.unknownFields);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            int size2 = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeBytesSize(1, getPackageFqNameBytes()) : 0;
            int dataSize = 0;
            for (int i = 0; i < this.shortClassName_.size(); i++) {
                dataSize += CodedOutputStream.computeBytesSizeNoTag(this.shortClassName_.getByteString(i));
            }
            int size3 = size2 + dataSize + (getShortClassNameList().size() * 1);
            int dataSize2 = 0;
            for (int i2 = 0; i2 < this.multifileFacadeShortNameId_.size(); i2++) {
                dataSize2 += CodedOutputStream.computeInt32SizeNoTag(this.multifileFacadeShortNameId_.get(i2).intValue());
            }
            int size4 = size3 + dataSize2;
            if (!getMultifileFacadeShortNameIdList().isEmpty()) {
                size4 = size4 + 1 + CodedOutputStream.computeInt32SizeNoTag(dataSize2);
            }
            this.multifileFacadeShortNameIdMemoizedSerializedSize = dataSize2;
            int dataSize3 = 0;
            for (int i3 = 0; i3 < this.multifileFacadeShortName_.size(); i3++) {
                dataSize3 += CodedOutputStream.computeBytesSizeNoTag(this.multifileFacadeShortName_.getByteString(i3));
            }
            int size5 = size4 + dataSize3 + (getMultifileFacadeShortNameList().size() * 1);
            int dataSize4 = 0;
            for (int i4 = 0; i4 < this.classWithJvmPackageNameShortName_.size(); i4++) {
                dataSize4 += CodedOutputStream.computeBytesSizeNoTag(this.classWithJvmPackageNameShortName_.getByteString(i4));
            }
            int size6 = size5 + dataSize4 + (getClassWithJvmPackageNameShortNameList().size() * 1);
            int dataSize5 = 0;
            for (int i5 = 0; i5 < this.classWithJvmPackageNamePackageId_.size(); i5++) {
                dataSize5 += CodedOutputStream.computeInt32SizeNoTag(this.classWithJvmPackageNamePackageId_.get(i5).intValue());
            }
            int size7 = size6 + dataSize5;
            if (!getClassWithJvmPackageNamePackageIdList().isEmpty()) {
                size7 = size7 + 1 + CodedOutputStream.computeInt32SizeNoTag(dataSize5);
            }
            this.classWithJvmPackageNamePackageIdMemoizedSerializedSize = dataSize5;
            int size8 = size7 + this.unknownFields.size();
            this.memoizedSerializedSize = size8;
            return size8;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(PackageParts prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PackageParts, Builder> implements PackagePartsOrBuilder {
            private int bitField0_;
            private Object packageFqName_ = "";
            private LazyStringList shortClassName_ = LazyStringArrayList.EMPTY;
            private List<Integer> multifileFacadeShortNameId_ = Collections.emptyList();
            private LazyStringList multifileFacadeShortName_ = LazyStringArrayList.EMPTY;
            private LazyStringList classWithJvmPackageNameShortName_ = LazyStringArrayList.EMPTY;
            private List<Integer> classWithJvmPackageNamePackageId_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* JADX INFO: renamed from: clone */
            public Builder mo379clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public PackageParts getDefaultInstanceForType() {
                return PackageParts.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public PackageParts build() {
                PackageParts result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public PackageParts buildPartial() {
                PackageParts result = new PackageParts(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.packageFqName_ = this.packageFqName_;
                if ((this.bitField0_ & 2) == 2) {
                    this.shortClassName_ = this.shortClassName_.getUnmodifiableView();
                    this.bitField0_ &= -3;
                }
                result.shortClassName_ = this.shortClassName_;
                if ((this.bitField0_ & 4) == 4) {
                    this.multifileFacadeShortNameId_ = Collections.unmodifiableList(this.multifileFacadeShortNameId_);
                    this.bitField0_ &= -5;
                }
                result.multifileFacadeShortNameId_ = this.multifileFacadeShortNameId_;
                if ((this.bitField0_ & 8) == 8) {
                    this.multifileFacadeShortName_ = this.multifileFacadeShortName_.getUnmodifiableView();
                    this.bitField0_ &= -9;
                }
                result.multifileFacadeShortName_ = this.multifileFacadeShortName_;
                if ((this.bitField0_ & 16) == 16) {
                    this.classWithJvmPackageNameShortName_ = this.classWithJvmPackageNameShortName_.getUnmodifiableView();
                    this.bitField0_ &= -17;
                }
                result.classWithJvmPackageNameShortName_ = this.classWithJvmPackageNameShortName_;
                if ((this.bitField0_ & 32) == 32) {
                    this.classWithJvmPackageNamePackageId_ = Collections.unmodifiableList(this.classWithJvmPackageNamePackageId_);
                    this.bitField0_ &= -33;
                }
                result.classWithJvmPackageNamePackageId_ = this.classWithJvmPackageNamePackageId_;
                result.bitField0_ = to_bitField0_;
                return result;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(PackageParts other) {
                if (other == PackageParts.getDefaultInstance()) {
                    return this;
                }
                if (other.hasPackageFqName()) {
                    this.bitField0_ |= 1;
                    this.packageFqName_ = other.packageFqName_;
                }
                if (!other.shortClassName_.isEmpty()) {
                    if (this.shortClassName_.isEmpty()) {
                        this.shortClassName_ = other.shortClassName_;
                        this.bitField0_ &= -3;
                    } else {
                        ensureShortClassNameIsMutable();
                        this.shortClassName_.addAll(other.shortClassName_);
                    }
                }
                if (!other.multifileFacadeShortNameId_.isEmpty()) {
                    if (this.multifileFacadeShortNameId_.isEmpty()) {
                        this.multifileFacadeShortNameId_ = other.multifileFacadeShortNameId_;
                        this.bitField0_ &= -5;
                    } else {
                        ensureMultifileFacadeShortNameIdIsMutable();
                        this.multifileFacadeShortNameId_.addAll(other.multifileFacadeShortNameId_);
                    }
                }
                if (!other.multifileFacadeShortName_.isEmpty()) {
                    if (this.multifileFacadeShortName_.isEmpty()) {
                        this.multifileFacadeShortName_ = other.multifileFacadeShortName_;
                        this.bitField0_ &= -9;
                    } else {
                        ensureMultifileFacadeShortNameIsMutable();
                        this.multifileFacadeShortName_.addAll(other.multifileFacadeShortName_);
                    }
                }
                if (!other.classWithJvmPackageNameShortName_.isEmpty()) {
                    if (this.classWithJvmPackageNameShortName_.isEmpty()) {
                        this.classWithJvmPackageNameShortName_ = other.classWithJvmPackageNameShortName_;
                        this.bitField0_ &= -17;
                    } else {
                        ensureClassWithJvmPackageNameShortNameIsMutable();
                        this.classWithJvmPackageNameShortName_.addAll(other.classWithJvmPackageNameShortName_);
                    }
                }
                if (!other.classWithJvmPackageNamePackageId_.isEmpty()) {
                    if (this.classWithJvmPackageNamePackageId_.isEmpty()) {
                        this.classWithJvmPackageNamePackageId_ = other.classWithJvmPackageNamePackageId_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureClassWithJvmPackageNamePackageIdIsMutable();
                        this.classWithJvmPackageNamePackageId_.addAll(other.classWithJvmPackageNamePackageId_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(other.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasPackageFqName()) {
                    return false;
                }
                return true;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                try {
                    try {
                        PackageParts parsedMessage = PackageParts.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        throw e;
                    }
                } catch (Throwable th) {
                    if (0 != 0) {
                        mergeFrom((PackageParts) null);
                    }
                    throw th;
                }
            }

            public boolean hasPackageFqName() {
                return (this.bitField0_ & 1) == 1;
            }

            private void ensureShortClassNameIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.shortClassName_ = new LazyStringArrayList(this.shortClassName_);
                    this.bitField0_ |= 2;
                }
            }

            private void ensureMultifileFacadeShortNameIdIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.multifileFacadeShortNameId_ = new ArrayList(this.multifileFacadeShortNameId_);
                    this.bitField0_ |= 4;
                }
            }

            private void ensureMultifileFacadeShortNameIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.multifileFacadeShortName_ = new LazyStringArrayList(this.multifileFacadeShortName_);
                    this.bitField0_ |= 8;
                }
            }

            private void ensureClassWithJvmPackageNameShortNameIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.classWithJvmPackageNameShortName_ = new LazyStringArrayList(this.classWithJvmPackageNameShortName_);
                    this.bitField0_ |= 16;
                }
            }

            private void ensureClassWithJvmPackageNamePackageIdIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.classWithJvmPackageNamePackageId_ = new ArrayList(this.classWithJvmPackageNamePackageId_);
                    this.bitField0_ |= 32;
                }
            }
        }
    }
}
